#!/usr/bin/perl

use strict;

exit(main(@ARGV));

sub usage {
  print STDERR <<EOM;
$0 [-n <namespace>]* [-s <split>] [-c <charset>] [-C] -m <mappings> <input> <output>
  <input> -- flat file to process
  <output> -- n3 file to write
  <split> -- perl regex to split fields, default /:/
  <mappings> -- list of <mapping>; split via /,\w*/
  <mapping> -- <rdf-predicate-name>[[;<type>];<prefix>]
  <type> -- integer, string, url, qstring, qurl; name; default is string
            when the type is name the prefix (default :) is prefixed on 
            the token.
  <charset> -- perl charset name of the input, default is iso-8859-1.
  -C -- do not discard lines that begin with '#', i.e. comments.
EOM
  }

sub main {
  my $splitter = ':';
  my $charset = 'iso-8859-1';
  my $discard_comments = 1;
  my @namespaces = ();
  my @mappings = (['string', ':f1'],
          ['string', ':f2'],
          ['string', ':f3'],
          ['string', ':f4'],
          ['string', ':f5'],
          ['string', ':f6'],
          ['string', ':f7'],
          ['string', ':f8'],
          ['string', ':f9'],
          ['string', ':f10'],
          ['string', ':f11'],
          ['string', ':f12'],
          ['string', ':f13']);
  my $input;
  my $output;
  while ( my $arg = shift ){
    if ($arg eq '-n'){
      my $namespaceDecl = shift;
      push @namespaces, $namespaceDecl;
    } elsif ($arg eq '-s'){
      $splitter = shift;
    } elsif ($arg eq '-m'){
      @mappings = ();
      my $parm = shift;
      for my $mapping ( split(/;\s*/, $parm) ) {
    my ($pred, $type, $prefix) = split(/,/, $mapping);
    $type = 'string' unless defined $type;
    $prefix = ':' unless defined $prefix;
    push @mappings, [$type, $pred, $prefix];
      }
    } elsif ($arg eq '-c'){
      $charset = shift;
    } elsif ($arg eq '-C'){
      $discard_comments = 0;
    } else {
      $input = $arg;
      last
    }
  }
  unless ( defined $input ){
    print STDERR "ERROR: No <input>\n";
    usage();
    return 1;
  }
  $output = shift;
  unless ( defined $output ){
    print STDERR "ERROR: No <output>\n";
    usage();
    return 1;
  }
  if ( $#mappings == 0 or $#_ > 0 )
    {
      print "$#mappings, $#_ ", join(':',@mappings), "\n";
      usage();
      return 1;
    }

  open(IN,"<:encoding($charset)","$input")
    or die "Unable to read '$input': $!";

  open(OUT,'>encoding(UTF-8)', "$output")
    or die "Unable for writting '$output': $!";

  for my $namespace (@namespaces){
    print OUT "$namespace\n";
  }
  while(<IN>){
    next if $discard_comments and /^#/;
    chop;
    my @fields = split(/$splitter/o, $_);
    my $pad = '';
    my @out;
    my $subject;
    my $first = 1;
    for my $mapping ( @mappings ){
      my $field = shift @fields;
      last unless defined $field;
      my $type = $mapping->[0];
      my $name = $mapping->[1];
      my $prefix = $mapping->[2];

      unless ($name eq 'drop'){
    my $value;
    if ( $type eq 'string' ){
      $value = qq("$field");
    } elsif ( $type eq 'integer' ){
      $value = 0 + $field;
    } elsif ( $type eq 'name' ){
      $value = $prefix . $field;
    } elsif ( $type eq 'uri' ){
      $value = "<$field>";
    } elsif ( $type eq 'quri' ){
      $value = $field;
    } elsif ( $type eq 'qstring' ){
      $value = $field;
    } else {
      die "Unknown type '$type' in mapping";
    }
    if ( $name eq 'subject' ){
      $subject = $value;
    } else {
      $pad .=  "; " unless $first;
      $first = 0;
      $pad .= "$name $value"
    }
      }
    }
    if ( defined $subject ){
      print OUT "$subject $pad .\n";
    } else {
      print OUT "[$pad].\n";
    }
  }

  return 0;
}
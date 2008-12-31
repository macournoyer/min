require "rake/clean"
require "spec/rake/spectask"

CLEAN.include "**/*.out", "**/*.output"

GENERATED_PARSER = "lib/min/generated_parser.rb"
CLOBBER.include GENERATED_PARSER

file GENERATED_PARSER => "lib/parser.y" do |t|
  if ENV['DEBUG']
    sh "racc -g -v -o #{t.name} #{t.prerequisites.first}"
  else
    sh "racc -o #{t.name} #{t.prerequisites.first}"
  end
end
desc "Generate the parser"
task :parser => GENERATED_PARSER

task :default => :spec

Spec::Rake::SpecTask.new do |t|
  t.spec_opts = %w(-fs -c)
  t.spec_files = FileList["spec/**_spec.rb"]
end
task :spec => :parser

desc "Show num LOC in lib"
task :loc do
  sh 'cat `find lib -name *.rb` | grep -v "^ *#" | grep -v "^ *$" | wc -l'
end
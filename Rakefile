require "rake/clean"
require "spec/rake/spectask"

GENERATED_PARSER = "lib/min/generated_parser.rb"
CLOBBER.include GENERATED_PARSER

file GENERATED_PARSER => "lib/parser.y" do |t|
  if ENV['DEBUG']
    sh "racc -g -v -o #{t.name} #{t.prerequisites.first}"
  else
    sh "racc -o #{t.name} #{t.prerequisites.first}"
  end
end
task :parser => GENERATED_PARSER

task :default => :spec

Spec::Rake::SpecTask.new do |t|
  t.spec_opts = %w(-fs -c)
  t.spec_files = FileList["spec/**_spec.rb"]
end
task :spec => :parser
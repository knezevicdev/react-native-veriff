
Pod::Spec.new do |s|
  s.name         = "RNVeriff"
  s.version      = "1.0.0"
  s.summary      = "RNVeriff"
  s.description  = <<-DESC
                  RNVeriff
                   DESC
  s.homepage     = "https://veriff.com/"
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "10.0"
  s.source       = { :git => "https://github.com/author/RNVeriff.git", :tag => "master" }
  s.source_files  = "RNVeriff/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  s.dependency "VeriffSDK", '~> 2.0.0'

end

  
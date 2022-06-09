#! /bin/bash -e

cat > generated_config.yml <<END
version: 2.1
orbs:
  eventuate-gradle-build-and-test: "eventuate_io/eventuate-gradle-build-and-test@0.2.1"
workflows:
  version: 2.1
  build-test-and-deploy:
    jobs:
END

for build_script in build-and-test-all*.sh ; do
cat >> generated_config.yml <<END
      - eventuate-gradle-build-and-test/build-and-test:
          name: $build_script
          script: ./$build_script
          maven_cache_command: ./compile-contracts.sh
          checksum_files: 'gradle.properties gradle/wrapper/gradle-wrapper.properties ./common-contracts/pom.xml'
END
done

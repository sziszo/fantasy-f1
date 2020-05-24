#!/usr/bin/env python3
import argparse
import subprocess
import sys
from pathlib import Path

import docker

from globals import DEFAULT_LOG_FOLDER


class QuarkusAppBuilder:

    def __init__(self, module_name, build_type, image_group, image_tag):
        self.module_name = module_name
        self.build_type = build_type
        self.image_group = image_group
        self.image_tag = image_tag

    def build(self):
        output_file = f'{DEFAULT_LOG_FOLDER}/{self.module_name}-{self.build_type}-build.out'
        with open(output_file, 'w+') as build_output:
            if self.build_type == 'jvm':
                print(f'start building {self.module_name} ', end='')
                self.exec_maven_command(command=["./mvnw", "clean", "package", "-Dquarkus.container-image.build=true",
                                                 f'-Dquarkus.container-image.group={self.image_group}',
                                                 f'-Dquarkus.container-image.name={self.module_name}-{self.build_type}',
                                                 f'-Dquarkus.container-image.tag={self.image_tag}',
                                                 "-pl", self.module_name],
                                        build_output=build_output)

            else:
                self.exec_maven_command(command=["./mvnw", "clean", "package",
                                                 "-Pnative", "-Dquarkus.native.container-build=true",
                                                 "-pl", self.module_name],
                                        build_output=build_output)

                source_dir = Path(__file__).resolve()
                dockerfile = source_dir / f'{self.module_name}' / 'src' / 'main' / 'docker' / f'Dockerfile.{self.build_type}'

                print(f'docker_file={dockerfile}')

                client = docker.from_env()
                client.images.build(path=f'{source_dir}',
                                    dockerfile=dockerfile.resolve(),
                                    tag=f'{self.image_group}/{self.module_name}-{self.build_type}:{self.image_tag}')

    @staticmethod
    def exec_maven_command(command, build_output):
        proc = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, universal_newlines=True)

        for line in proc.stdout:
            sys.stdout.write(line)
            build_output.write(line)

        proc.wait()
        if proc.returncode > 0:
            raise Exception('Build process failed!')


class Builder:

    def __init__(self, module_name=None, build_type='jvm', image_group='aklysoft', image_tag='latest'):
        self.module_name = module_name
        self.build_type = build_type
        self.image_group = image_group
        self.image_tag = image_tag

    def build(self):
        if not self.module_name or self.module_name == 'fantasy-f1-service':
            QuarkusAppBuilder('fantasy-f1-service', self.build_type, self.image_group, self.image_tag).build()


def main():
    parser = argparse.ArgumentParser(description='Building fantasy-f1 app',
                                     formatter_class=argparse.ArgumentDefaultsHelpFormatter)
    parser.add_argument("-m", "--module", help="set module", default='all', choices=['all', 'fantasy-f1-service'])
    parser.add_argument("-t", "--image-tag", help="set image tag", default='latest')
    parser.add_argument("-g", "--image-group", help="set image group", default='aklysoft')
    parser.add_argument("build_type", help="set build type", default='jvm', choices=['jvm', 'native'], nargs='?')
    args = parser.parse_args()

    Builder(None if args.module == 'all' else args.module, args.build_type, args.image_group, args.image_tag).build()


if __name__ == '__main__':
    main()

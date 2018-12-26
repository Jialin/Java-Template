# python IdeaProjects/ACM/src/template/gen.py

import fnmatch
import json
import os
import re
import string

TEMPLATE_EXT = 'template'
ROOT = '/Users/jialinouyang/IdeaProjects/ACM/src/template/'
SEPARATOR = '=' * 100
FILENAME = 'filename'
PACKAGE = 'template'
FILENAME_PATTERNS = ['(?<=public class )(\w+).*', '(?<=public abstract class )(\w+).*', '(?<=public interface )(\w+).*']


def find_templates(root):
    templates = []
    for _, dirnames, filenames in os.walk(root):
        for filename in fnmatch.filter(filenames, '*.' + TEMPLATE_EXT):
            templates.append((_, filename))
    return templates


for (root, filename) in find_templates(ROOT):
    if not root.endswith(PACKAGE):
        print('Template file (%s) should live in template packages!' % filename)
        continue
    print('Processing %s' % filename)
    lines = [line.rstrip('\n') for line in open(os.path.join(root, filename))]
    separator_line = [i for i, line in enumerate(lines) if line == SEPARATOR][0]
    template_root = root[: -len(PACKAGE)]
    for setting in json.loads(''.join(lines[: separator_line])):
        output_lines = []
        output_filename = ''
        for line in lines[separator_line + 1:]:
            parsed_line = line
            for (tag, value) in setting.items():
                parsed_line = string.replace(parsed_line, '%' + tag + '%', value)
            for pattern in FILENAME_PATTERNS:
                match = re.search(pattern, parsed_line)
                if match:
                    output_filename = match.group(1)
                    break
            output_lines.append(parsed_line + '\n')
        if not output_filename:
            print('Failed to compute the class name for %s' % filename)
            continue
        output_filename += '.java'
        if os.path.exists(template_root + output_filename) \
                and output_lines == open(template_root + output_filename, 'r').readlines():
            continue
        print('\tGenerating %s' % output_filename)
        with open(os.path.join(template_root, output_filename), 'w') as output_file:
            for line in output_lines:
                output_file.write(line)

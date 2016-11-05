import fnmatch
import json
import os
import string

TEMPLATE_EXT = 'template'

def findTemplates(root):
    templates = []
    for _, dirnames, filenames in os.walk(root):
        for filename in fnmatch.filter(filenames, '*.' + TEMPLATE_EXT):
            templates.append((_, filename))
    return templates;

ROOT = '/Users/jouyang/IdeaProjects/ACM/src/template/'
SEPARATOR = '=' * 100
FILENAME = 'filename'

for (root, filename) in findTemplates(ROOT):
    print 'Processing', filename
    lines = [line.rstrip('\n') for line in open(os.path.join(root, filename))]
    separatorLine = [i for i, line in enumerate(lines) if line == SEPARATOR][0];
    for setting in json.loads(''.join(lines[ : separatorLine])):
        outputFilename = setting[FILENAME] + '.java'
        print '\tGenerating', outputFilename
        with open(os.path.join(root, outputFilename), 'w') as outputFile:
            for line in lines[separatorLine + 1 : ]:
                parsedLine = line
                for (tag, value) in setting.items():
                    parsedLine = string.replace(parsedLine, '%' + tag + '%', value)
                outputFile.write(parsedLine + '\n')

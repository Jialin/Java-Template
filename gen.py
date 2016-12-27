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
PACKAGE = 'template'

for (root, filename) in findTemplates(ROOT):
    if not root.endswith(PACKAGE):
        print 'Template file (%s) should live in template packages!' % filename
        continue
    print 'Processing', filename
    lines = [line.rstrip('\n') for line in open(os.path.join(root, filename))]
    separatorLine = [i for i, line in enumerate(lines) if line == SEPARATOR][0];
    templateRoot = root[ : -len(PACKAGE)]
    for setting in json.loads(''.join(lines[ : separatorLine])):
        outputLines = []
        for line in lines[separatorLine + 1 : ]:
            parsedLine = line
            for (tag, value) in setting.items():
                parsedLine = string.replace(parsedLine, '%' + tag + '%', value)
            outputLines.append(parsedLine + '\n')
        outputFilename = setting[FILENAME] + '.java'
        if os.path.exists(templateRoot + outputFilename) and outputLines == open(templateRoot + outputFilename, 'r').readlines():
            continue
        print '\tGenerating', outputFilename
        with open(os.path.join(templateRoot, outputFilename), 'w') as outputFile:
            for line in outputLines:
                outputFile.write(line)


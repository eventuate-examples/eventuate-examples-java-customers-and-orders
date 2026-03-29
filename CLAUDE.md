# Project Guidelines

IMPORTANT: Before exploring the codebase, read [CODEBASE.md](CODEBASE.md) first. Use it as the navigation guide to locate packages, tests, skills, and feature docs.

## Executing bash commands

IMPORTANT: Use simple commands that you have permission to execute. Avoid complex commands that may fail due to permission issues.

When running scripts:
- Run scripts directly: `./script.sh` (NOT `bash ./script.sh`)
- Do not append `2>&1` to redirect stderr
- Do not use `git -C directory` - cd to the top-level directory and run git commands from there

When copying or moving files:
- Avoid compound commands with `&&` - run commands separately
- Avoid wildcard patterns (`*.java`) - copy files individually
- Single-file operations are more reliable with Bash permission system

## Skills

IMPORTANT: Always invoke the relevant skill before performing these actions:

- **Before creating git commits**: Use the `idea-to-code:commit-guidelines` skill
- **When practicing TDD**: Use the `idea-to-code:tdd` skill
- **When working from a plan file**: Use the `idea-to-code:plan-tracking` skill
- **When creating Dockerfiles**: Use the `idea-to-code:dockerfile-guidelines` skill
- **When moving/renaming files**: Use the `idea-to-code:file-organization` skill
- **When renumbering or editing plan file structure**: Use the `idea-to-code:plan-file-management` skill
- **When implementing or refactoring code**: Use the `idea-to-code:apply-design-patterns` skill
- **When investigating CI build failures**: Use the `idea-to-code:debugging-ci-failures` skill
- **When creating or updating GitHub Actions CI workflows**: Use the `idea-to-code:github-workflow-gradle-template` skill
- **When running tests in Java/Gradle projects**: Use the `idea-to-code:test-runner-java-gradle` skill
- **When writing test scripts involving infrastructure**: Use the `idea-to-code:testing-scripts-and-infrastructure` skill
- **When resolving merge or rebase conflicts**: Use the `idea-to-code:git-conflict-resolution` skill
- **When stuck on a problem**: Use the `idea-to-code:ask-a-friend` skill
- **When finding where code is defined or used**: Use the `idea-to-code:find-usage` skill
- **When running commands with verbose output**: Use the `idea-to-code:test-output-to-logfile` skill
- **When documenting a design pattern**: Use the `idea-to-code:write-design-pattern` skill
- **When capturing an idea from a discussion**: Use the `idea-to-code:write-idea` skill
- **When writing multiple similar files**: Use the `idea-to-code:incremental-development` skill

## Code Health

Before committing code changes, read and follow [CODE_SCENE.md](CODE_SCENE.md) for Code Health safeguard and refactoring instructions.

## Referencing Code Locations

- Use `path/to/file:line_number` when referencing a specific line (e.g., `src/i2code/plan/manager.py:42`).
- Use `path/to/file` when referencing a file without a specific line.
- Both formats enable click-to-navigate in the terminal.

## Code Style

- Prefer intention-revealing method names over comments. If you find yourself writing a comment to explain what code does, extract it into a method whose name conveys the intent.

## Tool Selection

IMPORTANT: Before running any Bash command, ask: "Is there a specialized tool for this?"

- File search → Glob (NOT find or ls)
- Content search → Grep (NOT grep or rg)
- Read files → Read (NOT cat/head/tail)

The specialized tools are faster, have correct permissions, and provide better output formatting.

## Git Commands

IMPORTANT: Always run git commands from the project root directory. If you need to operate on the repository, cd to the root directory first rather than using `git -C`. This prevents accidentally committing files outside the project root.

## Pattern-Based Fixes

When fixing issues caused by naming conventions or patterns:
1. Search the entire codebase for similar occurrences before making any changes
2. Fix ALL instances in a single commit
3. Never commit partial fixes for pattern-based problems

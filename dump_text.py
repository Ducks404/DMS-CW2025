import os

# Extensions to include
extensions = {'.java', '.fxml'}
# Folders to exclude
exclude_dirs = {'target', '.git', '.idea', 'test'}

with open('code_dump.txt', 'w', encoding='utf-8') as outfile:
    for root, dirs, files in os.walk("."):
        # Filter out excluded directories
        dirs[:] = [d for d in dirs if d not in exclude_dirs]
        
        for file in files:
            if any(file.endswith(ext) for ext in extensions):
                path = os.path.join(root, file)
                outfile.write(f"\n\n=== {path} ===\n\n")
                try:
                    with open(path, 'r', encoding='utf-8') as infile:
                        outfile.write(infile.read())
                except Exception as e:
                    outfile.write(f"Error reading file: {e}")
print("Done! Upload code_dump.txt")
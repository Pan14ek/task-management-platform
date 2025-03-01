# 🚀 Commit Message Convention

## 📌 General Rules
1. **Commit messages should be clear and descriptive.**
2. **Explain what and why was changed, not how.**
3. **Keep the title short (max. 72 characters).**
4. **Use English and lowercase (except module names).**
5. **If needed, add a more detailed description on the second line.**
6. **Avoid generic commit messages like `"Update"` or `"Fix"` without details.**

---

## ✍️ **Commit Message Structure**
```
<type>: <short description>

[Optional: More details if needed]
```
- **`type`** – describes the purpose of the commit (`feat`, `fix`, `refactor`, etc.).
- **`short description`** – briefly explains the change (without a period at the end).
- **`[Optional: More details]`** – add a detailed explanation if necessary.

---

## 🚀 **Commit Types**
| Type        | Description                                            | Example Commit |
|------------|--------------------------------------------------------|----------------|
| **feat**   | Adds a new feature (functionality).                    | `feat: add comment API` |
| **fix**    | Fixes a bug or issue.                                  | `fix: resolve NPE in CommentService` |
| **refactor** | Improves code structure without changing functionality. | `refactor: optimize task entity relationships` |
| **docs**   | Updates documentation (`README.md`, `CONTRIBUTING.md`). | `docs: update API documentation` |
| **style**  | Fixes styling issues (formatting, indentation, etc.).  | `style: fix indentation in TaskService` |
| **test**   | Adds or updates tests.                                 | `test: add unit tests for comment API` |
| **chore**  | Minor changes (CI/CD, `.gitignore`, dependencies, etc.). | `chore: update dependencies` |

---

## ✅ **Examples of Good Commit Messages**
✔ `feat: add JWT authentication to task service`  
✔ `fix: correct user validation in AuthController`  
✔ `docs: add commit convention to repository`  
✔ `test: add integration tests for task creation`

## ❌ **Bad Commit Messages**
❌ `update` (Update what?)  
❌ `fix` (What was fixed?)  
❌ `new feature` (Which feature?)

---

## 🎯 **Additional Notes**
- Use **`git rebase -i`** to edit commit messages if necessary.
- If the commit introduces **breaking changes**, include `BREAKING CHANGE:` in the description.

```
feat: migrate task service to Kafka

BREAKING CHANGE: Removed old REST-based task updates.
```

---

## 📌 **Final Tips**
- **Describe what was changed, not how it was implemented.**
- **Follow a consistent style across all commits.**
- **Pull request merges should have meaningful names (`Merge pull request #4 from Pan14ek/add-kafka-support`).**

---
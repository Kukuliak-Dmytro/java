# Code Coverage Guide

## What is Code Coverage?

Code coverage measures how much of your source code is executed when your tests run. It shows:
- **Line Coverage**: Which lines of code were executed
- **Branch Coverage**: Which branches (if/else, switch) were taken
- **Method Coverage**: Which methods were called

## Using IntelliJ IDEA's Built-in Coverage

IntelliJ IDEA has excellent built-in code coverage support - no additional setup needed!

### Running Tests with Coverage

#### Method 1: Right-Click Menu
1. Right-click on a test class (e.g., `FlowerTest.java`)
2. Select **`Run 'FlowerTest' with Coverage`**
   - Or use the dropdown next to the Run button → `Run with Coverage`

#### Method 2: Keyboard Shortcut
1. Select a test class or test method
2. Press **`Ctrl+Shift+F10`** (or `Ctrl+Shift+F10` for Windows)
3. Choose **`Run with Coverage`** from the dropdown

#### Method 3: Run All Tests with Coverage
1. Right-click on the `test` folder
2. Select **`Run 'All Tests' with Coverage`**

### Viewing Coverage Results

After running tests with coverage, you'll see:

#### 1. Coverage Tool Window
- Opens automatically at the bottom of IntelliJ
- Shows coverage statistics by package/class
- Displays percentages for:
  - **Lines**: Percentage of lines executed
  - **Branches**: Percentage of branches taken
  - **Methods**: Percentage of methods called

#### 2. Color-Coded Source Code
- **Green**: Code that was executed (covered)
- **Red**: Code that was NOT executed (not covered)
- **Yellow**: Partially covered (some branches taken, others not)

#### 3. Coverage Statistics
Click on any class in the Coverage tool window to see:
- Total lines
- Covered lines
- Coverage percentage
- Uncovered lines (listed)

### Understanding Coverage Percentages

- **80-100%**: Excellent coverage
- **60-79%**: Good coverage
- **40-59%**: Moderate coverage (consider adding more tests)
- **0-39%**: Poor coverage (definitely needs more tests)

### Coverage Types Explained

1. **Line Coverage**: 
   - Measures if a line was executed at least once
   - Example: If `if (x > 0)` is executed, the line is covered

2. **Branch Coverage**:
   - Measures if both true and false paths were taken
   - Example: For `if (x > 0)`, both `x > 0` (true) and `x <= 0` (false) cases should be tested

3. **Method Coverage**:
   - Measures if a method was called at least once
   - Doesn't guarantee all code paths in the method were tested

### Tips for Better Coverage

1. **Aim for High Coverage, But Don't Obsess**
   - 100% coverage doesn't mean perfect tests
   - Focus on testing important business logic

2. **Test Edge Cases**
   - Null values
   - Empty collections
   - Boundary values (0, negative numbers, max values)

3. **Test Error Paths**
   - Invalid inputs
   - Exception handling
   - Error conditions

4. **Don't Test Everything**
   - Simple getters/setters might not need tests
   - Focus on complex logic and business rules

### Exporting Coverage Reports

1. In the Coverage tool window, click the **Export** button (floppy disk icon)
2. Choose format:
   - **HTML Report**: View in browser
   - **XML Report**: For CI/CD integration
3. Select location and save

### Coverage Settings

To configure coverage:
1. `File` → `Settings` → `Build, Execution, Deployment` → `Coverage`
2. Options:
   - **Coverage runner**: Choose between IntelliJ's built-in or JaCoCo
   - **Show coverage data for test sources**: Include test code in coverage
   - **Activate coverage view**: Auto-open coverage window

## Example: Checking Your Project Coverage

1. Run all tests with coverage:
   - Right-click `test` folder → `Run 'All Tests' with Coverage`

2. Review the results:
   - Check which classes have low coverage
   - Look for red (uncovered) lines in your source code
   - Identify methods that need more tests

3. Improve coverage:
   - Add tests for uncovered methods
   - Test edge cases for partially covered code
   - Focus on business-critical code first

## Common Coverage Scenarios

### Scenario 1: Constructor Not Covered
- **Problem**: Default constructor shows 0% coverage
- **Solution**: Add a test that creates an object using the default constructor

### Scenario 2: Error Handling Not Covered
- **Problem**: `catch` blocks show 0% coverage
- **Solution**: Add tests that trigger exceptions

### Scenario 3: Private Methods
- **Note**: Private methods are covered indirectly through public methods
- If a private method isn't covered, it means no public method calls it
- Consider if the method is needed, or add a test that exercises it through public API

## Limitations

- Coverage shows what was executed, not if it was tested correctly
- 100% coverage ≠ bug-free code
- Some code (like main methods, UI code) is hard to test
- Focus on meaningful coverage, not just numbers


---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# TAssist Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project was adapted from [AB3](https://se-education.org/addressbook-level3/) (source code provided [here](https://github.com/nus-cs2103-AY2425S2/tp))

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.tassist.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* CS2106 Teaching Assistants (TAs)
* Manage multiple tutorial groups per semester
* Need access to student information to track participation, assignments, and communicate with students
* May want to identify and support weaker students who require extra attention
* Has a need to manage a significant number of contacts
* Prefer desktop apps over other types
* Can type fast
* Prefers typing instead of mouse interactions
* Is reasonably comfortable using CLI apps

**Value proposition**: CS2106 TAs struggle to efficiently track progress and access student information with multiple tutorial groups and many students in each. The lack of a centralized system makes management and follow-ups tedious. TAssist provides a keyboard-driven platform that streamlines student organization into meaningful groups, enabling access to key details anytime, anywhere.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​        | I want to …​                                                            | So that I can…​                                                                                                                                                                                         |
|----------|----------------|-------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `*`      | new user       | access a walkthrough on basic app functionalities                       | learn how to use the app proficiently without struggling to figure it out on my own                                                                                                                     |
| `*`      | new user       | access basic help documentation                                         | assess if the application will be useful for my needs                                                                                                                                                   |
| `*`      | new user       | see an introductory video of the app                                    | learn about key features and basic usage of the application                                                                                                                                             |
| `* * *`  | user           | add a student's record                                                  | note their particulars and begin keeping track of their attendance and/or lab scores                                                                                                                    |
| `* * *`  | user           | update a student's tutorial attendance                                  | maintain accurate administrative records.                                                                                                                                                               |
| `* * *`  | user           | udpate a student's grades for labs                                      | identify if they are struggling with course content and may require additional guidance. This also helps me keep track of graded assignments without rummaging through all the scripts in my workspace. |
| `*`      | user           | tag a student by home faculty                                           | manage administrative records and identify those with less                                                                                                                                              
| `*`      | user           | tag a student by current performance                                    | identify those who may need additional academic support.                                                                                                                                                |
| `* * *`  | user           | delete a student record                                                 | remove dropped students, incorrect entries, or outdated records.                                                                                                                                        |
| `* *`    | user           | edit a students record                                                  | minimize chance of someone else seeing them by accident                                                                                                                                                 |
| `* * *`  | user           | view a list of all students                                             | minimize chance of someone else seeing them by accident                                                                                                                                                 |
| `*`      | user           | filter/search students by name                                          | locate a specific student.                                                                                                                                                                              |
| `*`      | user           | filter/search students by tutorial / lab group                          | find all students in a class.                                                                                                                                                                           |
| `*`      | user           | filter/search students by home faculty                                  | see the distribution of students across faculties.                                                                                                                                                      |
| `*`      | user           | filter/search students by year                                          | see the distribution of students by cohort.                                                                                                                                                             |
| `*`      | user           | filter/search students by performance                                   | identify students who may need help.                                                                                                                                                                    |
| `*`      | user           | export my data to JSON                                                  | store it in a structured, parseable format.                                                                                                                                                             |
| `*`      | user           | export my data to CSV                                                   | store it in an easily readable format.                                                                                                                                                                  |
| `*`      | user           | receive prompts when typing in commands                                 | be reminded of missing fields.                                                                                                                                                                          |
| `*`      | user           | receive a changelog of updates                                          | stay informed about new features.                                                                                                                                                                       |
| `*`      | lazy user      | perform batch operations for updating attendance                        | N.A.                                                                                                                                                                                                    |
| `*`      | lazy user      | perform batch operations for updating participation                     | N.A.                                                                                                                                                                                                    |
| `*`      | lazy user      | perform batch operations for updating grades                            | N.A.                                                                                                                                                                                                    |
| `*`      | lazy user      | perform batch operations for deleting records by their logical groupings | remove all relevant records                                                                                                                                                                             |
| `*`      | lazy user      | perform batch operations for deleting all records                       | start afresh with a new user profile                                                                                                                                                                    |
| `*`      | lazy user      | import data from a CSV file                                             | quickly load data from existing records without manually establishing a new database for it                                                                                                             |
| `*`      | lazy user      | abbreviations for commands                                              | be more proficient upon familiarizing with the application                                                                                                                                              |
| `*`      | lazy user      | add keyboard shortcuts                                                  | customize the application to the user's preferences for higher efficiency                                                                                                                               |
| `*`      | careless user  | automate backups                                                        | rollback on royal mess ups                                                                                                                                                                              |
| `*`      | careless user  | undo and redo what i just did                                           | revert when i make mistakes                                                                                                                                                                             |
| `*`      | careless user  | receive warning messages before deleting multiple records               | avoid destroying the whole database from a messed-up command                                                                                                                                            |
| `*`      | organized user | hide details of a student record that I do not need by default       | avoid cluttering the user interface                                                                                                                                                                     |

*{More to be added}*

### Use cases

For all use cases below, the **System** is the `TAssist` and the **Actor** is the `user`, unless specified otherwise

**Use case: UC01 - View all students**

**MSS**

1.  TAssist displays all students

    Use case ends.


**Use case: UC02 - Add a student**

**MSS**

1. User requests to add a student into the list.
2. TAssist validates the input data.
3. TAssist adds the student to the database.
4. TAssist displays the confirmation message.

    Use case ends.

**Extensions**

* 2a. TAssist detects only mandatory arguments provided for a student record within user input.

    Use case resumes at step 3.

* 2b. TAssist detects missing mandatory arguments required for a student record within user input.

    * 2b1. TAssist shows an error message, requesting for provision of missing arguments.

    * 2b2. User enters missing arguments.

    Steps 2b1 and 2b2 are repeated until all mandatory arguments are valid.

    Use case resumes at step 3.

* 2c. TAssist detects invalid/incorrect format arguments in user input.

    * 2c1. TAssist shows an error message, either specifying format requirements or a list of valid arguments.

    * 2c2. User corrects input.

    Steps 2c1 and 2c2 are repeated until the input provided is valid.

    Use case resumes at step 3.

* 2d. TAssist detects duplicate student entry based on user input.

    * 2d1. TAssist shows an error message.

    * 2d2. User enters new data.

    Steps 2d1 and 2d2 are repeated until the input is valid.

    Use case resumes at step 3.

*a. At any time, user clears input. 
    Use case ends.

**Use case: UC03 - Delete a student**

**MSS**

1.  User requests to delete a specific student
2.  TAssist deletes the student

    Use case ends.

**Extensions**

* 1a. User provides only the mandatory arguments in the
  correct format, or provides additional arguments on top
  of the mandatory arguments, all in the correct format.

  Use case resumes at step 2.

* 1b. User does not provide at least one mandatory argument
  required to delete a student.

    * 1b1. TAssist shows an error message, requesting for missing arguments.

    * 1b2. User enters new data.

  Steps 1b1 and 1b2 are repeated until the data entered are correct.

  Use case resumes at step 2.

* 1c. User provides at least one argument that is invalid, or in incorrect format.
  For instance, user provides an index that is out of range.

    * 1c1. TAssist shows an error message, requesting for valid arguments in correct format.

    * 1c2. User enters new data.

  Steps 1c1 and 1c2 are repeated until the data entered are correct.

  Use case resumes at step 2.

* 1d. User requests to delete a student that is not in the database.

    * 1d1. TAssist shows an error message, saying the student has already been deleted.

  Use case ends.

**Use case: UC04 - Mark attendance for a student**

**MSS**

1.  User requests to mark attendance for a student in the list, for some week
2.  TAssist marks the student as attended for the indicated week

    Use case ends.

**Extensions**

* 1a. User provides only the mandatory arguments in the
  correct format, or provides additional arguments on top
  of the mandatory arguments, all in the correct format.

  Use case resumes at step 2.

* 1b. User does not provide at least one mandatory argument
  required to mark a student's attendance.

    * 1b1. TAssist shows an error message, requesting for missing arguments.

    * 1b2. User enters new data.

  Steps 1b1 and 1b2 are repeated until the data entered are correct.

  Use case resumes at step 2.

* 1c. User provides at least one argument that is invalid, or in incorrect format.
  For instance, user provides an index or week number that is out of range.

    * 1c1. TAssist shows an error message, requesting for valid arguments in correct format.

    * 1c2. User enters new data.

  Steps 1c1 and 1c2 are repeated until the data entered are correct.

  Use case resumes at step 2.

* 1d. User requests that the student be marked as not attended.

    * 1d1. TAssist marks the student as not attended for the indicated week.

      Use case ends.

* 1e. User requests that the student be marked as on MC.

    * 1e1. TAssist marks the student as on MC for the indicated week.

      Use case ends.


**Use case: UC05 - Update lab score for a student**

**MSS**

1.  User requests to update lab score for a student in the list, for some lab session
2.  TAssist updates the lab score for the student for the indicated lab session

    Use case ends.

**Extensions**

* 1a. User provides only the mandatory arguments in the
  correct format, or provides additional arguments on top
  of the mandatory arguments, all in the correct format.

  Use case resumes at step 2.

* 1b. User does not provide at least one mandatory argument
  required to update a student's lab score.

    * 1b1. TAssist shows an error message, requesting for missing arguments.

    * 1b2. User enters new data.

  Steps 1b1 and 1b2 are repeated until the data entered are correct.

  Use case resumes at step 2.

* 1c. User provides at least one argument that is invalid, or in incorrect format.
  For instance, user provides an index that is out of range, or an invalid lab session name.

    * 1c1. TAssist shows an error message, requesting for valid arguments in correct format.

    * 1c2. User enters new data.

  Steps 1c1 and 1c2 are repeated until the data entered are correct.

  Use case resumes at step 2.

* 1d. User requests that the lab score be updated for all students in a lab session.

    * 1d1. TAssist updates the lab score for all students for the indicated lab session.

      Use case ends.

**Use case: UC06 - Load data from file**

**MSS**

1.  User opens the app.
2.  TAssist loads data from a file stored at a specified location.
3.  TAssist displays all students and their information, as loaded from file.

    Use case ends.

**Extensions**

* 2a. File at specified location is missing.

    * 2a1. TAssist displays no student data.

    Use case ends.

* 2b. File at specified location cannot be parsed, due to corrupted data in file.

    * 2b1. TAssist displays an error message for non-parsable data records.

    * 2b2. TAssist displays the remaining students that are parsable.

  Use case ends.

* 2c. File at specified location contains duplicate entries.

    * 2c1. TAssist displays an error message for duplicate entries.

  Use case resumes at Step 3.

* 2d. File at specified location contains entries exceeding the maximum number of entries allowed.

    * 2d1. TAssist displays an error message that the file contains too many entries.

    * 2d2. Starting from the first entry, TA shows up to the maximum number of entries, and omits
      all the entries after that.

  Use case ends.

* 2e. File at specified location contains entries nearing the maximum number of entries allowed.

    * 2e1. TAssist displays an error message, with the number of entries left that the user
      can add, before it reaches the maximum number of entries.

  Use case resumes at Step 3.

**Use case: UC07 - Save data to file**

**MSS**

1.  User closes the app.
2.  TAssist saves students' data into a file, at a specified location.

    Use case ends.


### Non-Functional Requirements

1. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
2. Ideally entirely keyboard driven, with minimal mouse clicks required.
2. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
3. Should respond to user input relatively quickly (under 1s).
3. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
4. Data should be stored locally and should be in a human editable text file, not involving the use of a Database Management System.
5. Should save backups occasionally.
6. Should follow Object-oriented paradigm primarily.
7. Should not depend on a personal remote server and function completely offline.
8. Should work without requiring an installer.
9. Should work well for standard screen resolutions 1920x1080 and higher and of screen scales 100% and 125%.
10. Should be functional for screen resolutions 1280x720 and higher and of screen scales 150%.
11. Product should be under 100MB, while documents should be under 15MB/file.
12. Should have PDF-friendly developer guides and user guides.

### Glossary
* **Command**: An action that the user calls to run a specified function.
It may return a result and it may also take in a specified number of arguments.
* **Component**: A [Name] component has its API in a [Name].java interface and implements its functionality
using the [Name]Manager.java class following the [Name] interface.
* **Database**: The save file of this program.
* **Database entry**: An item saved in the database. Commonly linked to a contact. One contact is one database entry.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Sequence diagram**: a diagram that shows the flow of the program.
* **TA**: Teaching Assistant

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_


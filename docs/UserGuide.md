---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TAssist User Guide

TAssist is a **desktop app designed for CS2106 Teaching Assistants (TAs) to manage student records, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface(GUI). It provides:
* a simple, intuitive UI
* quick access to crucial student records, such as their contact, attendance and current grades
* high efficiency for fast typers
* human editable file formats for data persistence

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for TAssist.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar TAssist.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add -n John Doe -p 98765432 -e johnd@example.com -m A0123456J -tut T01` : Adds a contact named `John Doe` to TAssist.

   * `del -i 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add -n NAME`, `NAME` is a parameter which can be used as `add -n John Doe`.

* Items in parentheses requires at least one of them to be supplied.<br>
  e.g `(-p PHONE_NUMBER -tg TELEGRAM_HANDLE)` means either `-p PHONE_NUMBER` or `-tg TELEGRAM_HANDLE` or both `-p PHONE_NUMBER -tg TELEGRAM HANDLE` are accepted.

* Items in square brackets are optional.<br>
  e.g `-n NAME [-tag TAG]` can be used as `-n John Doe -tag friend` or as `-n John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[-tag TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `-n NAME -p PHONE_NUMBER`, `-p PHONE_NUMBER -n NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add -n NAME (-p PHONE_NUMBER -tg TELEGRAM_HANDLE) -e EMAIL -m MATRICULATION_NUMBER (-t TUTORIAL_GROUP -b LAB_GROUP) [-f FACULTY] [-y YEAR_OF_STUDY] [-r REMARKS] [-tag TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0).
Tags must be a single word consisting of alphanumeric characters only.
</box>

Examples:
* `add -n John -p 81234567 -tg @jornn -e e1234567@u.nus.edu -m A1234567X -t T02 -b B03 -f Computing -y 5 -r Likes to sing`
* `add -n Doe -tg @doe_a_deer -e e7654321@u.nus.edu -B B01 -m A7654321J`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Marks attendance: `att`

Marks the attendance of an individual student, or a tutorial group.

Format: `att (-i INDEX -t TUTORIAL GROUP) -w WEEK [-mc] [-u] [-nt]`

* Conditional parameters: EITHER `-i INDEX` OR `-t TUTORIAL GROUP`
  * Not accepted: NEITHER or BOTH FLAGS TOGETHER
* Mandatory parameter: `-w WEEK`
  * Not accepted: MISSING week flag
* Optional parameters: EITHER ONE OF `-mc`, `-u`, OR `-nt`
  * Not accepted: TWO OR MORE OF THE ABOVE FLAGS
* Additional restriction: `-nt` CANNOT be together with `-i`.

Assuming the above restrictions are satisfied,
* Marks the attendance of a student (if `-i INDEX` is provided)
  OR all students in a tutorial group (if `-t TUTORIAL GROUP` is provided).
* The new attendance status is ATTENDED by default. However:
  * If `-mc` is provided, new attendance status is ON MC.
  * If `-u` is provided, new attendance status is NOT ATTENDED.
  * If `-nt` is provided, new attendance status is NO TUTORIAL.

Examples:
* `att -i 1 -w 3` marks the first student as attended Tutorial Week 3.
* `att -i 2 -w 10 -mc` marks the second student as on MC for Tutorial Week 10.
* `att -t T01 -w 1 -nt` marks the whole tutorial group T01 as No Tutorial for Tutorial Week 1.
  * This means each student in tutorial group T01 has his attendance updated to No Tutorial.

Images used are courtesy of:
Check Icon: https://www.iconsdb.com/white-icons/checkmark-icon.html
Cross Icon: https://www.iconsdb.com/white-icons/x-mark-icon.html
Ban Icon: https://www.iconsdb.com/white-icons/ban-icon.html

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `del`

Deletes the specified person from the address book.

Format: `del -i INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `del -i 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `del -i 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add -n NAME (-p PHONE_NUMBER -tg TELEGRAM_HANDLE) -e EMAIL -m MATRICULATION_NUMBER (-t TUTORIAL_GROUP -b LAB_GROUP) [-f FACULTY] [-y YEAR_OF_STUDY] [-r REMARKS] [-tag TAG]…​` <br> e.g., `add -n John -p 81234567 -tg @jornn -e e1234567@u.nus.edu -m A1234567X -t T02 -b B03 -f Computing -y 5 -r Likes to sing`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Mark Attendance**   | `att (-i INDEX -t [TUTORIAL GROUP]) [-mc] [-u] [-nt]`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`

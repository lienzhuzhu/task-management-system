# Implementation Notes

## Broken contract

User index page incorrectly accessed `data.total*` instead of `data.page` for `totalElements` and `totalPages` _FIXED_

## CreateTaskDTO

The User.java entity / User table does not enforce unique values for the name column, so when creating a Task, let's just have the end user provide the user ID number.

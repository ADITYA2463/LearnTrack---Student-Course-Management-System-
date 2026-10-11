# Design Notes

## Why use ArrayList instead of an array?

An array has a fixed size after it is created. `ArrayList` can grow or shrink as records are added or removed. LearnTrack's number of students, courses, and enrollments is not known in advance, so `ArrayList` is a simple fit. It also provides methods such as `add`, `get`, and `size`.

## Where are static members used and why?

`IdGenerator` has static counters and static methods. IDs are generated for the whole application, not separately for each `IdGenerator` object, so static members make sense. The service classes share the same generator.

## Where is inheritance used and what is gained?

`Person` stores fields common to people: ID, first name, last name, and email. `Student` extends `Person`, so it reuses those fields and methods instead of duplicating them. `Student` overrides `getDisplayName()` to add student-specific information. This demonstrates inheritance and runtime polymorphism.

## Encapsulation

Entity fields are private. Other classes read or update them through public getters and setters. This keeps the internal data representation controlled and makes the classes easier to maintain.

## Separation of concerns

- `entity`: holds data and basic entity behavior.
- `service`: performs application operations and validates business rules.
- `ui`: displays menus, reads input, and calls services.
- `util`: contains shared helper functionality.
- `exception`: contains custom exception types.

The UI does not directly manage the internal lists; it asks service classes to do that work.

## Exception handling

The console UI validates numbers and catches input errors. Services throw `EntityNotFoundException` for missing records and `InvalidInputException` when a requested operation violates a business rule. The UI displays a helpful message instead of allowing the application to terminate unexpectedly.

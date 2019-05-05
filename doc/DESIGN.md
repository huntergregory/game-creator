## Design Document

#### Overview
The main design goal of this project was flexibility in authorship, simple communication between the player and engine, and generality of design to allow integration with online servers.

All of these files are created by the authoring environment. The authoring environment is designed so that the user must create entities, labeled objects in the environment, so that instances of these object can then be placed. Then, logic for  each instance, object, or scene depending on what is currently selected will be placed into the terminal at the bottom of 
the authoring environment. For those who are not familiar with GUI scripting, popups have been added to make adding object scripts, key events, scene scripts, and collisions easier. 

Once all of these instances, objects, scenes, and their logic
have been placed/written, the game can now be serialized by Gson into a game object that is ready to be passed through a file or the cloud.

The network accounts feature communicates through webrequests to a Google app engine backend. JSON files are returned that tell things such as if a login request was compatible, and if it was what were the user's name, high scores, and more. All of this was loaded into a UserIdentity object, which was loaded into the GameCenter and put into a pane that displayed the user information.

The game center holds the author and a player, and a player gets game data either from the game center or from the author. Then it calls on the engine.

The engine takes in the game data, and converts it to engine data. The engine data is the model which the player can display, and the player uses it to run each iteration of a game loop, updating the model.
 The engine also provides the author with event classes which the author can employ in scripting to handle default events.


#### Making a Game

Making a game mainly lies in creating game objects, from which you can create instances. This consists of dragging and dropping instances. You also have to set resources, and create the engine's data types.

Creating data types mainly relies on scripting.

In general, scripts use a keyword instead of a specific instance or id to preserve flexibility. For example, if we had more time, we could devise a way to save scripts and apply the same script for a collision between mario and a turtle as well as mario and a goomba. 

An outline of where scripts are used in the engine outside of parsing:
- An object/instance's *Logic Component*, where the engine executes this logic during every iteration of the game loop. Keyword = "instance"
- *Key Events*, where the user specifies a key and action that effects the user. Keyword = "user"
- *Collision Events* defined between two object types. Keyword = "object1", "object2", "collisionDetector". The collision detector allows you to write a conditional based on a collision happening in any combination of the four directions.
- *Scene Rules*, where logic such as changing between scenes is executed every game loop.

The manager keyword exists in all these scripts. It allows the author to call default methods. Here's an example script for a collision event: "manager.call('Die', object1)".

The engine also parses the author's data into these data types, as well as all the components for each object/instance. All the following use the keyword "parser".

- *Scene Logic*, where key events, collision events, and level rules are created. The respective methods for these are `addLevelRules()`, `addCollision()`, and `addKeyEvent()`.
- *Object Logic*, where components are added and set at runtime in the engine.
- The engine creates instances from their parent object. This is done, then *Instance Logic* is parsed, where more component values can be set for individual instances.

The author can also call `getComponent()` and modify or get certain values with any instance in any of these scripts or in the object or instance logic.

To make the authoring environment more user friendly GUIs were added that could be activated to make it easier to add components to objects and instances, as well as key events and collisions and such. For example, to add a collision initially a user would have to input
```java
parser.addCollision(x, y, script)
```
where x and y are strings of the ids of two different game objects and the script is a string specifying either a default event or a user defined manipulation of components. 

#### Adding New Features
###### Engine
Add a new component by simply extending the component class. 
It will automatically be bound to groovy scripts if its in the Component class' package or in a subpackage.

To add a new default event, extend any abstract Event class and then override an abstract execute method and include the events parameter types in the constructor. 
If your event modifies or accesses a component, extend the abstract ComponentDependentEvent, which checks to see that the instance has the component classes passed into the event constructor under the hood.
Abstract events can be created for events that use similar methods or modify the same component to reduce code duplication.


###### Author

###### Player

###### Game Center


#### Design Decisions & Trade-offs


#### Assumptions 
One large assumption is that key events processed in the engine only affect the defined user. This prevented the author from 
having to specify the user for key events, which we believed would only ever impact the user in a scroller.



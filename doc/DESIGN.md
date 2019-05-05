Design Document

All of these files are created by the authoring environment. The authoring environment is designed so that the user must create entities, labeled objects in the environment, so that instances of these object can then be placed. Then, logic for  each instance, object, or scene depending on what is currently selected will be placed into the terminal at the bottom of 
the authoring environment. For those who are not familiar with GUI scripting, popups have been added tomake adding object scripts, key events, scene scripts, and collisions easier. 


Instead of writing this whole line, using the GUI, the user could simply put the two entity types in the x and y fields, and specify the script. While this still requires knowledge of how the engine works, this got around some of the annoying syntax. 

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

Once all of these instances, objects, scenes, and their logic
have been placed/written, the game can now be serialized by Gson into a game object that is ready to be passed through a file or the cloud.

The network accounts feature communicates through webrequests to a Google app engine backend. JSON files are returned that tell things such as if a login request was compatible, and if it was what were the user's name, high scores, and more. All of this was loaded into a UserIdentity object, which was loaded into the GameCenter and put into a pane that displayed the user information.
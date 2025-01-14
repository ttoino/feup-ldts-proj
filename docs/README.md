# **LDTS_805 - Out of the Breach**

<p align="center">
    <img src="/others/outofthebreach1.jpg">
</p>

---

Out of the Breach is a Java based video game that draws inspiration from the turn-based indie title [Into the Breach](https://subsetgames.com/itb.html) from Subset Games.

In Out of The Breach (ITB) you take the role of the aliens from Into the Breach, while keeping the same core mechanics from the original game, such as defending infrastructures and using the environment as your weapon.

This project is part of the LDTS (2020/21) unit of the Bachelor of Informatics and Computing Engineering course [@FEUP](https://github.com/FEUP) and its goal is applying the ideal design patterns and philosophies to the code that is written. The theme of the project was chosen by the group formed by [António Santos (202008004)](https://github.com/toni-santos), [João Pereira (202007145)](https://github.com/ttoino) and [Pedro Silva (202004985)](https://github.com/pedrosilva17).

---
## Features

<br/>

### Implemented  Features

<!-- TODO: ADD PICTURES -->

> Sprites-ish: An implementation of sprites similar to the original game!

> Enemy types: Enemies can have several abilities and have an archetype ("Cannon", "Tank", "Flying").

> Terrain types: Different terrain types have different effects on the strategy of the player.

<p align="center">
    <img src="/others/attack.png"/>
</p>


> Menus: Add a main menu screen.

<p align="center">
    <img src="/others/mainmenu.png"/>
</p>

> Mouse controls: Just like in Into the Breach the main control scheme follows a point and click system.

> Responsive enemies: Enemy intelligence will have different behaviour according to their archetypes.

> Environmental elements: Abilities will push entities and they will behave accordingly, per example, pushing an enemy against a mountain deals damage to the mountain and the enemy being pushed.

<p align="center">
    <img src="/others/move.png"/>
</p>


### Planned Features

> Random map generation: Randomly generate a new map with new enemies each new game!

### Neat things we added

> Transparency: some enemies and UI elements have transparent parts (pretty interesting considering the limitations imposed by Lanterna)

<p align="center">
    <img src="/others/ground_trans.jpg"/>
    <img src="/others/water_trans.jpg">
</p>

<br/>

---

<br/>

## Design

<br/>

Going into this project the general design philosophy adopted was MVC, however this solutiuon quickly became unmanagable as the code we were writing was not only really hard to mantain but it also turned out to not lend itself that well to the type of game we were making. As we delved deeper into constructing views and started working on controllers the quality of the code rapidly decreased and we were unhappy with the results we were obtaining.

Given this dilemma we searched for a solution that would not only replace MVC but also allow us to keep some of the code and work we had already done thus far, and that's how we built the solution below. The following UML illustrates the general design philosophy of our project, shifting the focus of our efforts from expressively separating the information, the visualizations and the logic to creating an harmony between the elements of our game, their data and their presentation, whilst separating the intrinsic logic that ties them together.

<p align="center">
    <img src="/others/trans_uml.svg"/>

| Color  | Design Pattern |
| :----: | :------------: |
|  Pink  |   Composite    |
| Orange |    Strategy    |
|  Blue  |     State      |
| Green  |    Observer    |
</p>

Associations between the classes that constitute the Observer pattern are simplified to allow for better readability. In the same vein, associations with the Vector class and between the Sprite class are also omitted.

This drastic shift in the general approach of the problems we were dealing with came as a double edged sword, while it allowed for a great understanding of the code we were producing and made us more confident of the direction we were heading, as it resolved many of the problems we faced with MVC, it came with its' own challenges, such as maintaining an understandable and reasonable separation of concerns. While it was easy to simply bundle up the functionality of each piece of the puzzle in itself it was also difficult to maintain a clear head and correctly accomodate its individual piece its functions in the right place.

<br/>

### Rendering

<br/>

- Problem in context

    A core part of video games is their presentation and ITB's is exquisite, using beautifully detailed sprites of units, terrains and other elements in tandem with an isometric perspective to convey depth the game as an unique style not easily replicable, especially with the graphical interface used for this project (Lanterna). 

    Keeping in mind the importance of the visual identity of the original game we wanted to try to translate as much of the original artstyle as possible given the limitations imposed. For that task it was obvious from the beggining that we had to use sprites of some sort for our game as, even though the game would most likely still be playable and the actions on screen be understandable, the spirit of the game would be lost. So while ditching the forced 3D-ish perspective of the original and sticking to a conventional top-down 2D view we ventured forth in implementing sprites into the game, thankfully this task was made easier by the community of Into the Breach and their app for getting the game's assets, this allowed us to use the original sprites of the aliens and mechs, but we had to redesign some of the terrains models.

    All of this was pretty simple however the challenge truly started when figuring out the structure of the code as it was crucial that we created a solution that could be easily scalable and gave us the freedom to alter it if push came to shove.

- The Pattern

    Diving into the solution of this problem it was obvious that correctly separating and attributing the correct responsabilites to each element of our code needed to be our utmost priority, the first issue was understanding what was responsible for rendering all of our elements, that's how the solution of creating an interface for renderers was born. A Renderer is responsible for knowing how to draw anything, may it be a square or a sprite.

    However this would be useless if we had nothing to actually render, that's where Renderables come into play, elements that implement this class have a method render() that is defined by themselves. This means each unit and terrain type has a method on how to draw themselves, and is notified by a renderable when to do so. The UML below better illustrates this solution.

    <p align="center">
        <img src="/others/render_uml.png"/>
    </p>

- Implementation

    [Render](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/tree/main/src/main/java/pt/up/fe/ldts/ootb/gui/render) - Directory with all of the classes mentioned;

    [Renderable](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/gui/render/Renderable.java) - One of the interfaces mentioned previously; 

    [Renderer](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/gui/render/Renderer.java) - One of the interfaces mentioned previously;

    [Sprite](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/gui/render/Sprite.java) - Record class of a sprite with Lanterna's restrictions;

    [SpriteLoader](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/gui/render/SpriteLoader.java) - Class responsible for loading sprites;

- Consequences
    
    The approach can cause some problems when scaling and changing code, or even implementing some features, that will be discussed in the Code Smells section in the end.

<br/>

### Inputs

<br/>

- Problem in context

    Designing a well suited control scheme for our game was important, as it is important that the player feels comfortable and at ease with their own tools and their information is correctly transmitted to the one in control.

    The tried-and-tested solution was using mouse controls and since it worked so well we decided to stick with it, however Lanterna has very bad mouse support, almost none in fact, hence creating a reliable and efficient solution for this problem was important. 

- The Pattern

    Due to the previously mentioned lackluster support for mouse controls of the tools at our disposal the solution we found was to use an observer pattern where classes that handle user input register themselves as such and through events and listeners are notified of mouse movement, mouse clicks and keyboard inputs, according to their own registration.

    <p align="center">
        <img src="/others/input_uml.png"/>
    </p>


- Implementation

    [Input](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/tree/main/src/main/java/pt/up/fe/ldts/ootb/gui/input) - Directory with all of the classes mentioned;

    [Input Handler](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/gui/input/InputHandler.java) - Interface that implements all of the input management units;

    [Mouse Move Listener](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/gui/input/MouseMoveListener.java) - Listener for mouse movements;

    [Mouse Click Listener](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/gui/input/MouseClickListener.java) - Listener for mouse clicks;

    [Keyboard Listener](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/gui/input/KeyboardListener.java) - Listener for keyboard inputs;

    [Mouse Move Event](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/gui/input/MouseMoveEvent.java) - Event of a mouse movement;

    [Mouse Click Event](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/gui/input/MouseClickEvent.java) - Event of a mouse click;
    
    [Keyboard Event](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/gui/input/KeyboardEvent.java) - Event of a keyboard input;

- Consequences

    For how elegant this implementation is it also has it shortcomings, mostly related to how well the terminal handles inputs, such has not reading some clicks.

<br/>

### The game flow

<br/>

- Problem in context

    In Into the Breach there is a specific flow to combat and the turns in which actions are taken. The gane begins with the player seeing the map they are facing and placing their units, then the enemies move and announce their attack, giving visual clues for the player to guide themselves and plan their approach to the situation, the player then moves and attacks, in whichever order they choose and finally the enemies attack/do their actions and the cycle repeats: enemies plan, player reacts, enemies execute... Knowing the importance that this entails for the playability and enjoyability of the game we wanted to keep a similar design whilst making some small adjustements that while not completely changing the way the game is played are different enough to make for an interesting spin on the formula.

    With that being said, the implementation we went with focused on keeping the general framework of the game as it is in the original whilst being easier to implement and making up new challenges, therefore: players are no longer able to attack/move in whatever order, moving units is always prior to attacks, and both actions can be skipped. This means players need to plan their movement accordingly.

- The Pattern

    In order to deal with this challenge we saw fit to use a state pattern, that cycles between all of the phases of combat, this in tandem with the menus mentioned previously allowed us to easily divide the game's internal logic parts of the application, such as getting to start the game and ending it. 

    The implementation can be represented by the following pattern:

    <p align="center">
        <img src="/others/state_uml.png">
    </p>

    As shown by the UML the implementation allows the game to flow between phases and intrinsicaly, without the need of any other interfaces, simply the game and its elements.

    The game, no matter what level the player is in, always starts in an InitialState, which is only visited this one time per level, displaying the starting position of the enemies and a prompt to start playing.

    The enemies then move and plan their attacks in the BeforePlayerState, here we also check if the game as ended if a few specific conditions are met and transition to a LostState or a WinState accordingly, in case neither win or loss conditinos are met the game continues on.

    The player is finally actionable and is able to move their units in the MoveState and perform attacks in the AttackState, in that order, both of these phases can be skipped ahead into the next phase by the player.

    Finally, the enemies finish their planned actions and some more checks are made to assure if the win/loss conditions are met and transition to their respective states, if not the loop proceeds ahead to the BeforePlayerState.

- Implementation

    [States](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/tree/main/src/main/java/pt/up/fe/ldts/ootb/game/state) - Directory with all of the classes mentioned;

    [State](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/state/State.java) - Interface for all states;

    [BasePlayerState](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/state/BasePlayerState.java) - Abstract class implementing methods common to all of the players actions;

    [AfterPlayerState](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/state/AfterPlayerState.java);

    [AttackState](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/state/AttackState.java);
    
    [BaseState](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/state/BaseState.java);
    
    [BeforePlayerState](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/state/BeforePlayerState.java);
    
    [InitialState](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/state/InitialState.java);
    
    [LostState](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/state/LostState.java);
    
    [MoveState](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/state/MoveState.java);
    
    [WinState](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/state/WinState.java);

- Consequences

    While this solution is elegant it is rigid and does not allow for easy changes to the game flow, implementing the same approach to turns as the one implemented by ITB would be much harder to do so given how much each state is dependent on its preceding and succeeding ones.

<br/>

### Abilities

<br/>

- Problem in context

    Abilities are a core part of the gameplay loop of Into the Breach, they are effectively the primary means of interacting with the game world and solving the challenges posed to the player, therefore making having a well defined strategy when creating them was extremely important.
    
    Abilities are complex instruments that have multiple functions such as pushing, dealing damage, targetting multiple squares... So implementing them in a fashion that is modular and easy to implement is key, especially when new ideas arise during development.
    

- The Pattern

    With all this in mind when implementing a solution to this problem we decided to take an approach of mixing the simplicity of implementation of a facade-like pattern with the modularity of the composite pattern.

    The "facade" pattern allows us to create a new Ability by simply implementing Base Ability with a different combination of Area, Effect and AreaOfEffect and we can then on call upon that new ability to implement on our creatures. They currently only have a single ability but that is simply fixed/realized by getting them a list of abilities instead of a single instance of an ability.

    In the following UML we are able to see an example of the implementation of the composite design pattern in effects.

    <p align="center">
        <img src="/others/composite_uml.png">
    </p>

    This implementation may seem a bit confusing at first, but in nature is pretty simple. As previously stated an ability is complex and can have multiple effects and complex targetting options, to accomodate for this we can create a composite of effects, an ability is able to not only damage a creature but also push it, in practice we create a new CompositeEffect that takes in however many effects we may want! 
    
- Implementation

    [Abilities](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/tree/main/src/main/java/pt/up/fe/ldts/ootb/game/ability) - Directory with all of the available abilites, and subdirectories for each attribute of an ability;

    [AOE](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/tree/main/src/main/java/pt/up/fe/ldts/ootb/game/ability/aoe) - Directory with all of the available implementations of the area of effect parts of an ability;

    [Effect](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/tree/main/src/main/java/pt/up/fe/ldts/ootb/game/ability/effect) - Directory with all of the available implementations of the effect parts of an ability;

    [Range](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/tree/main/src/main/java/pt/up/fe/ldts/ootb/game/ability/range) - Directory with all of the available implementations of the range parts of an ability;

    [Ability](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/ability/Ability.java) - Interface of an ability;

    [BaseAbility](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/ability/BaseAbility.java) - Abstract class from which all abilities are made out of;

    The abilities that were created for the game can be seen in this [folder](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/tree/main/src/main/java/pt/up/fe/ldts/ootb/game/ability), for brevity sake those will be kept from this section, as well as all of the implemented ranges, AOEs and effects, which can be seen in their respective folders.

- Consequences
    
    We are extremely happy with how well this feature was implemented, it allows for great customization and is easy enough for someone that is not familiar with the underlying code to implement and start creating new combinations, or even make new effects/ranges/AOEs. The implementation of the composite pattern could still be improved and even easier to mess around with. 

<br/>

### Enemy Intelligence

<br/>

- Problem in context

    ITB is a single player game and that was something we wanted to stick with when making our game, as this helps define a specific goal for the game, defeat the bad guys, and makes possible interactions more predictable and easier to implement, a computer will only do whatever we program it to do while a human may act unpredictibaly. This was a challenge that needed to be kept in mind from the beginning, as writing code that isn't generic enough to accomodate for player and enemy units would lead to several problems, as we were discovering when using MVC as our design philosophy.

- The Pattern
  
    Taking this into consideration and the restrictions, not only related to the delivery dates but also the tools at our disposal, we took the approach of implementing a strategy pattern, a solution that doesn't require much work and is easier to implement than an actual adaptive intelligence, and of course mutable.

    <p align="center">
        <img src="/others/strategy_uml.png">
    </p>

- Implementation

    [Robot](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/tree/main/src/main/java/pt/up/fe/ldts/ootb/game/entity/creature/robot) - Directory with all of the classes mentioned;

    [Strategies](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/tree/main/src/main/java/pt/up/fe/ldts/ootb/game/entity/creature/robot/strategy) - Directory with all of the strategies implemented;

    [NearestStrategy](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/entity/creature/robot/strategy/NearestStrategy.java) - Strategy that chooses the moves that position the enemy closer to the player, using randomness as a fallback strategy;

    [RandomStrategy](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/src/main/java/pt/up/fe/ldts/ootb/game/entity/creature/robot/strategy/RandomStrategy.java) - Randomly moves and attacks;

- Consequences
  
    As previously stated this solution is not the most interesting in terms of gameplay or creation of interesting game situations, however, we found it well suited to the dimensions of the project and are happy with how easy it was to implement new strategies.

---

<br/>

## Code Smells

<br/>

- ### Concerning rendering

    One of the pressing questions of software engineering revolves around the separation of concerns, this topic was predominant when working on this project but it reared its' ugly head when organizing the structure that guides how we render elements of the game. Should each entity know how to render itself, or should we find a compromise that allows us to implement a single instance and make this problem generic enough to create an abstraction of what each unit truly is? 
    
    We found both of these approaches valid, both of them would in fact fit our needs, however we chose to keep the individuality of each element of our game, as such each element is a Renderable, it has a method render() in which we specify how it should be rendered.

    #### While this solution is intuitive it comes at some costs:
    
    - Hard to scale;
    - Tweaks need to be made to each element of the game, instead of only being done in a global scale;
    - New elements need to implement this exact same method, which might not make sense in all cases;
  
    Despite this, it made for some very easy to read and understand code when actually rendering things on to the screen, also giving us a way to make each part of the game unique. Not only that but it seems reasonable to us that, in the context of our general code architecture, this approach would fit nicely allowing for a lot of freedom to how we built our code surrounding this exact problem.

- ### Loading the game

    Currently the menu is responsible for loading the game, this is by far a solution in line with the rules of the separation of concerns. Idealy there would be a class or part of our code that would handle these exact problems, such as starting, stopping, skipping or quiting the game, however due to time constraints we were unable to properly implement a solution that would adhere to all of the requirements that this particular problem entails.

---

<br/>

## Testing

<br/>


<p align="center">
    <img src="./others/pitest.png">
</p>

### [Pitest was used to study the coverage and mutation percentages of our code](https://github.com/FEUP-LDTS-2021/ldts-project-assignment-g0805/blob/main/docs/pitest/index.html)

### PBT was also implemented in several testing cases.
---

<br/>

## Self-Evaluation

<br/>

| António Santos | João Pereira | Pedro Silva |
| :------------: | :----------: | :---------: |
|       35       |      40      |     25      |

Code was mainly developed in Live Share/Code With Me sessions, hence the single author commits.
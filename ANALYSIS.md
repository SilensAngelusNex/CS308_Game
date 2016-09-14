# Game Analysis
### Project Journal
#### Time Review
***
* ***Time Spent:*** I started the project on August 26 and finished on September 12. I worked a total of 20-30 hours, which was about 5-6 hours more than I had planned for, so I ended up taking advantage of the 10AM deadline when I was trying to finish up at midnight.  
***Time Allocation:*** * Most of the extra time was spent adding JavaDoc strings and refactoring variable names form abbreviations to more descriptive names. I spent 2-3 hours (of the total) planning, 3-4 hours refactoring and adding DocStrings, and the rest was spent coding or debugging, in about a 60-40 split.
* ***Code Management:*** I didn't put much thought into managing the code, since I was the only one working on it. I tried to commit and push whenever I got to a major milestone, like completing a feature, but many of my commits (the early ones) have work on multiple features. I realized part way through the project that this was pretty bad form. I normally pushed after each commit.


#### Commits
***
* ***Commit Time and Size:*** I made 36 commits over 12 days. They seem to have an average size of about 150 lines.
* ***Story:*** I think it tells a pretty informative story about how the code evolved. It is a bit sparse towards the beginning.
*  ***Describe Commits:***
    * ***Make characters only able to act on their own faction's turn.***  I refactored variable Names in ChapterMap, added a few methods to ChapterMap to check whose turn it is before moving and keep track of whose turn it is, added a location getter to Character, and added methods to Party to add Characters to a Party and check if a Character is in a Party.
        * *Purpose:* Before this commit, any player could move any Character, not just characters in their Party. Players should only be able to move characters that are a part of their team.
        * *Packing:* I packaged these changes together because the all "belonged" to the same feature.
        * *Manageability:* The commit page on GitLab is only a few pages, so I'd say it's fairly manageable. It's 90 lines.
    * ***Make people die when they are killed and make the star[t] menu work correctly.*** I added two methods and a variable in ChapterMap, two variables and a getter for each in Character, added three methods in Party, and updated some code in Example game to make use of the changes.
        * *Purpose* This commit made it so characters would be removed from the map when they died and so players could use the start menu to end the turn.
        * *Packing* These changes were packed in the same commit because the features were related functionally, so I worked on them one after the other.
        * *Manageability* This commit is probably easier to follow than the other; it's longer, but the changes are more concentrated.


#### Conclusions
***
* ***Size Estimate:*** I absolutely underestimated the project size. I should allow more time to work on the UI in the future.
* ***Editing:*** The UI required the most editing because there were so many little errors to make and gotchasmto remember. The rest of the code was much more simple.
* ***Self Improvement:*** To be a better designer, I should work on adding documentation as I go.
* ***Project Improvement:*** My MercenaryWars class is an unholy mess. It really needs to be separated into multiple subclasses, one for each "state." I also probably overused the key handler when I should have been relying on the step function.


### Design Review
#### Status
***
* ***Consistency:*** The code is definitely internally consistent ignoring the the MercenaryWars class, which has a different organizational style.
* ***Readability:*** It does what *I* would expect, so hopefully other people have the same expectations as I do. I think the methods have names that accurately describe their function, which should help.
* ***Dependancies:*** All the dependencies arise through a class containing an instance of another, inheritance, or the use of my Util class, which seems clear.
* ***Examples***
	* *Character* This class contains all the information to a specific character and methods to change this information depending on  what happens in the game. It extends ImageView, so the character can directly alter their position in the scene. It unfortunately contains a large number of methods, because so much is required from a character, but many of these methods simply call methods of the fields a Character instance contains, which makes it a bit more manageable. The methods where the bulk of the logic is contained in Character are the ones that need Character's fields to interact, or the ones dealing with the ImageView.
	* *EquipmentSet* This class is easier to read than Character, simply because there is less going on. However, the EquipmentSet deals with all the weapon equipping/dequipping that Character (through Inventory) delegates to it and with allowing the Character to access the bonuses provided by the equipment.

#### Design
***
	* ***Overall Structure*** The overall structure is that the UI (MercenaryWars) accesses a level (ChapterMap), which contains two teams (Party) of Characters, as well as two 2D arrays that represent the current state of the level's map. Characters contain a handful of statistics about how good they are at different things, as well as a collection of items.
	* ***Adding Levels*** To add a new level, you would have to add a new starting state initialization function, like ChapterMap.newMultiplayer. You might want to do the same thing with characters, so your new level could have new characters. Then, you would have to make a way to access your new level in MercenaryWars. The easiest way would probably be to just replace one of the existing levels with your own, but you make it start after the victory screen with a little more work.
	* ***Features***
		* *Distinct Modes* For distinct modes, the ChapterMap needed a few more method to allow for AI, if it was turned on to decide where to move on it's turn and the MercenaryWars class needed and additional "if" statement in the step function to check if the AI should make a move.
		* *Winning/Losing Conditions* Adding clear winning and losing conditions was pretty similar. Party needed a method to check if the Party had lost, ChapterMap needed a method to check if any Parties had lost a game, and MercenaryWars got a change to the step function to react if anyone had won.
		* *Splash Screen* MercenaryWars was the only class (of mine) involved in the splash screen. The splash gets added to the screen in the init function, then removed my keypresses in the key handler function.
#### Alternate Designs
***
	* ***Design Decisions***
		* *Redraw everything each tick vs redraw things as user moves them* Redrawing things each tick allows for cleaner key event management code, easier animation, and simultaneous actions, but comes at the cost of storing a lot more data in Character about where the character is and where they are moving. Also, the simultaneous action upside isn't relevant to my game. Redrawing as the user moves things allows for a more simple step function, which should improve performance (probably a negligible amount). It also is easier conceptually, but comes at the cost of a lot of **almost** duplicated code. I would prefer to redraw things each step, as long as performance would allow it, but code for redrawing as the user moves things was easier to write, so I did that instead.
		* *Static methods to initialize "standard" instances vs initialize them where they are needed or subclasses* The static methods (for ChapterMap, Character, and Terrain) newXXXX, where XXXX is the name of the new thing the method will return, allowed me to reuse levels, characters, and terrain without copying the massive constructor that goes with them. This did, especially in Character, clog up the class file with a bunch of long static methods. I could have initialize them where they were needed, but that would have just moved the clogging, while creating duplicated code. I also could have mad subclasses which just called the super constructor with specific arguments, but then I'd have a whole bunch of extra class files that wouldn't really do anything. I prefer the static methods just because it makes more organizational sense to me.
	* ***Bugs*** I'm not aware of any game-breaking bugs (I would have fixed them.), but here are three things that should be changed moving forward.
		* Redrawing on user input -> each game tick
		* Lower hierarchical levels communicate with scene (Character tells scene to show damage, healing, death animations, etc)
		* Victory screen ignores user input (It should let you exit, play again, go to the next level, etc.)

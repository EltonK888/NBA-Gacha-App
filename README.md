# NBA Gacha :basketball:

## A gacha game with NBA players built in Java for the CPSC 210 project

### How it works
Assemble your best team of 15 NBA players of different positions by rolling for players in the gacha. Face off against
other teams to see whose team is better!

### Who will use it
This game will interest people that are fans of basketball and have experience with gacha games/fantasy sports.
It provides a twist on the traditional fantasy basketball game and incorporates the excitement of rolling with
gacha games.

### Why this project interests me
I have enjoyed watching and playing basketball for a couple of years now. Every year, I enjoy playing NBA fantasy
against my friends and see who can assemble the best roster and poke fun at those whose team falls apart. I was
inspired to make this a gacha game as I've seen discord bots which were essentially gacha games and thought it would
be cool if I could make something similar. So I combined my interest for basketball with the elements of a gacha game
and this idea was born.


### User Stories
- As a user, I want to be able to roll for different NBA players. :heavy_check_mark: Done **<- grade this one**
- As a user, I want to be able to choose to either claim the player that I roll, or skip him. :heavy_check_mark: Done
 **<- grade this one**
- As a user, I want the players that I claim in the gacha to be added to my claimed players collection.
 :heavy_check_mark: Done **<- grade this one**
- As a user, I want to be able to see all the players I have claimed. :heavy_check_mark: Done **<- grade this one**
- As a user, I want to be able to see the all players on my current team. :heavy_check_mark: Done optionally grade this
if a user story is to fail
- As a user, I want to be able to swap players I have claimed with players on the active team :heavy_check_mark: Done
optionally grade this if a user story is to fail
- As a user, I want to be able to see my chances at rolling different tiers of players. :heavy_check_mark: Done
- As a user, when I quit the application, I want my claimed players and my active team roster to be saved 
:heavy_check_mark: Done
- As a user, when I open the application, I want my saved data to be automatically loaded so I can see my players
:heavy_check_mark: Done
- As a user, I want to be able to claim duplicate rolls of a player to enhance their skills. TODO
- As a user, I want to be able to choose which players I can add to my team based on their position. TODO
    - Players can be one of 5 different positions 
        1. Point Guard (PG) - the ball handlers
        2. Shooting Guard (SG) - the shooters
        3. Small Forward (SF) - the jack of all trades, master of none
        4. Power Forward (PF) - Good rebounders and scoring
        5. Center (C) - Great rebounders, shot blockers
- As a user, I want to be able to face off against other teams in a game and see which team wins/has the highest scores
TODO
- As a user, I want to be able to see the stats my players put up after the game. TODO

### Phase 4: Task 2
In my project I have implemented the following phase 4 criteria:

1. A type hierarchy with `ClaimedPlayers` and `Team` that both extend the superclass `PlayerRoster`. `ClaimedPlayers`
and `Team` overrides the `addPlayer()` since the roster type differs in functionality in how many players they can have.
2. I used a `Map` in the `NbaGachaApp.java` code to return the player rosters in one data structure. I also used
a `Map` to store the player stats e.g. "rebounds" maps to the avg rebounds per game of a player.

### Phase 4: Task 3
The following is a list of things I would refactor/improve if I were given more time to work on the project
- Currently, the GUI logic is all in one class. The cohesion of the GUI class is very low as it is responsible for
multiple functions of the GUI. I would refactor this to separate the different functionality into different classes. For
example, I would try to have a separate class that deals with setting up the button action listeners.
- Redesigning the type hierarchy to include a bidirectional relationship with `Team` and `ClaimedPlayers`.
`ClaimedPlayers` and `Team` have to interact with each other as players can be swapped from `ClaimedPlayers` to `Team`
and vice versa. In hindsight, I think a bidirectional relationship between these two classes would be a good idea as
when one player is added to a roster, it is subsequently taken off the other roster.
- When creating the GUI, I had to get the rolled player information from the `player.toString()` method. However, now
that I look back on this, I realize this is bad design as it introduces coupling between the `GUI` and `Player` classes. 
Instead, I should have just used the `Player` getters to retrieve the information about the player.
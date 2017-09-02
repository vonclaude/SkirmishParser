# SkirmishParser
An action phase parser for skirmish games. It's an executable JAR file that runs in the console.

To run the parser: 

1. Double-click the startparser.cmd icon in the jar folder.
2. Copy the thread URL into the console window that opens.
3. Copy the post number of the post which starts the current action phase when prompted.

Output:

The program will output a single player's list of actions at a single time. Pressing any key will advance forward through the list of posts parsed. You should note what the final action for the turn will be and stop when you see it or the program will close out of the console (this is a feature for later development).
	
What this program doesn't support:

- Parsing previously resolved action phases: The parser will run from the starting post, to the end of the thread. It will not break separate phases apart in any way.
- Erroneous action formatting: All actions and ONLY ACTIONS MUST be formatted as greentext to be parsed correctly. Any non-action formatted as greentext will cause the parser to catch that post as an action post (and likely display blank, for the moment, hit enter to proceed).
- Spoiler text in actions: Spoiler text will not be caught by the parser, even if included in a greentext action.
- Statblocks: Player statblocks are a mess of formatting and I'm not going to even begin trying to develop a thing for those.

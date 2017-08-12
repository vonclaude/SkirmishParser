# SkirmishParser
An action phase parser for skirmish games. It's an executable JAR file that runs in the console.

To run the parser: 

1. Double-click the startparser.cmd icon in the jar folder.
2. Copy the thread URL into the console window that opens.
3. Copy the post number of the post which starts the current action phase when prompted.
4. Type "true" or "false" in response to the final question.
	"true": All actions will display in the console one player at a time. Press enter to display the next player's action in the console window.
	"false": All actions for the phase will display at once in a list.
	
What this program doesn't support:

- Exception handling: Errors will break everything, as will incorrect inputs
- Parsing previously resolved action phases: The parser will run from the starting post, to the end of the thread. It will not break separate phases apart in any way.
- Erroneous action formatting: All actions and ONLY ACTIONS MUST be formatted as greentext to be parsed correctly. Any non-action formatted as greentext will cause the parser to catch that post as an action post.
- Spoiler text in actions: Spoiler text will not be caught by the parser, even if included in a greentext action.
- Statblocks: Player statblocks are a mess of formatting and I'm not going to even begin trying to develop a thing for those.

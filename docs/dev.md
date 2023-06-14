## 05/13
- Encounted an issue where the GUI isn't updating properly with rooms.
**Solved, as of 05/14**
- Discovered a bug that when the client, run from the IDE reconnects to the server, it force disconnects that client. It is due to the server seeing two open connections on the server.

(05/14) - Mitch - Something I thought of is that this could be an issue if say, a user connects to the server, plays a bit, DCs and then trys to reconnect later, the server might refuse it.

**Ongoing, not major issue**

## 05/14

- Solved room GUI not updating properly
- Added messaging support to rooms
- Found a bug - overflowing the messages JPanel with messages will push the messages input field, and the start game button, off the page.
**Ongoing, not major issue**
- Found a bug - once a message is sent to the chat, it doesn't clear the text box.
**Solved, as of 05/18**

## 05/18
- Solved text field not clearing. `field.setText("");` did the trick

## 05/22
- Started work on the main game's GUI and gameplay

## 05/23-06/01
- Designed 3 new UI screens, and wrote up the requirements to finish the project.
- Programmed the basis for planets, stations and interactions.
- Developed coordinate saving

## 06/05
- Finished Space Station functionality, working on planets

## 06/07
- Many Changes
- Planets done
- Fast Travel finished
	- If the fast travel window is closed, it is impossible to get it back lol, non vital issue. Ill fix it later idrc
- Space Stations finished
- Preparing for prod!

**Current goal is completion! Polish can be done later!**

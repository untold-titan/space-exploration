WCS is a language specification designed by Mitchell Miller to make communication between server and client super easy. 

# The Spec
Commands are constructed in the following format:
`command-name:parameters`
For example, the message to join any room with space is:
`join-room:`
Notice how the parameters are empty, as this version of the command doesn't require any parameters.
An example command to share data with the server, for example, a player's status, would look like this:
`player-status:username,10,4,495`
This is formatted just like a command, but has multiple sections, dedicated to data. 

# Best Practices
It is recommended to note, either in a seperate document, or in the code itself, how each command, when sent, is structured. One example of this:
`conn.send("player-status" + player.username + player.score + player.turn + player.coins);`
This line doesn't require any extra notation, because the line explains itself.
A line this this though:
`conn.send("player-status" + a + b + c + d);`
Requires a comment above the line explaining the format of the command, and what parameters are being sent.

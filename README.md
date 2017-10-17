Lilypad Utilities
================

Lilypad Utilities is a bare bone Minecraft plugin built for the Lilypad networks, it allows you to quickly to run normal Bukkit/Spigot commands on the global scale.

## Versions

| Application | Version |
|:---:|:---:|
| Bukkit/Spigot | 1.12 R1 |
| Lilypad Proxy | 167 |
| Lilypad Connect | 66 |

## Features

 - Broadcast messages to everyone on the entire network.
 - Find anyone anywhere (If they're online).
 - See what servers are online and how many players on on them.
 - Send a player to any server you want.
 - Talk to your staff across the entire network with the staff chat.
 - Customizable messages, change any success message sent by the plugin.

## How to install

 1. Drag and drop the jar file into your plugin folders for the servers you want to connect via Lilypad Utilities
 2. Start up your Lilypad connect, proxy and the servers you added the plugin to.
 3. ???
 4. Profit, you're now done, that was easy right?

## Commands

 - /alert \<messae\>
   - **Description**: Broadcasts an alert/message to every server on the network who has Lilypad Utilities installed.
   - **Permission**: lilyutils.admin.alert
   - **Aliases**: /galert
 - /find \<player\>
   - **Description**: Find the player on the network if they're online.
   - **Permission**: lilyutils.admin.find
 - /glist
   - **Description**: Displays all servers connected to Lilypad that has Lilypad Utilities installed, and how many players are connected to the server.
   - **Permission**: _This command does not have a permission requirement_
 - /send \<player\> \<server\>
   - **Description**: Sends the given player to the given server if the player AND server are both online.
   - **Permission**: lilyutils.admin.send
 - /staffchat \<player\>
   - **Description**: Sends a message to every server that has Lilypad Utilities installed, only players with the permission node below are able to read the message that was sent.
   - **Permission**: lilyutils.admin.chat
   - **Aliases**: /staffc, /sc

## License

Lilypad Utilities is open-sourced software licensed under the [MIT license](http://opensource.org/licenses/MIT).
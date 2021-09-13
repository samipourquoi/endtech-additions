# EndTech Additions

Mod which adds small features for both the server and the client. Made for EndTech.

# Features
## Custom Objectives
Add 4 new scoreboard criterias: `minecraft.custom:minecraft.all`, `…picks`, `…shovels`, `…axes`. 
These will count every block mined with these tools, no matter the type. `…all` will count every block mined, no matter the tool!

Block tag scoreboards: Custom objectives that count by block type\
Ex: `minecraft.custom:minecraft.used_trapdoors` will count every trapdoor used, no matter the type\
Automatically create and update block tag scoreboards using [every-scoreboard](https://github.com/samipourquoi/every-scoreboard)

## Scoreboard Totals
Add totals to scoreboards: `et-additions scoreboardTotals true`, `...false`.\
This adds a display on the sidebar to show the total of all tracked players for that objective

## Perfect trades highlighter
An easy way to see if a trade is perfect. If it is, it will add a yellow arrow instead of the normal white one. Only supports books
at the moment.

![perfect trades](https://imgur.com/lbsjQ54.png)

## Endbot Autocomplete
Autocompletes [Endbot](https://github.com/samipourquoi/endbot) commands, and includes
the [Every Scoreboard](https://github.com/samipourquoi/every-scoreboard) naming scheme.

![autocomplete](https://media.giphy.com/media/Nq9y4cfgJqSlxAJJXh/giphy.gif)

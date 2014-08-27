AutoCommand is an advanced command handling framework for Bukkit. AutoCommand is under MIT license. See LICENSE for more information.

What makes AutoCommand outstanding is parameter handling. It includes API for converting Strings to other useful classes, which makes it very simple to use.

Example commands class:
```
	public class UtilityCommands {
		@AutoCommand(
            	console = true, 
            	player = true, 
            	permission = "example.give", 
            	usage = "<player> <item> <amount>"
        )
    	public void give(CommandSender sender,Player target, Material item, int amount) {
			...
    	}

    	@AutoCommand(
        	    console = true, 
            	player = true, 
            	permission = "example.strike", 
            	usage = "<player>"
       	)
    	public void strike(CommandSender sender, Player otherPlayer) {
			...
    	}
	}
```

## Features

+ Annotations-based command processing
+ Permission handling via [PermissionsGuru] (https://github.com/tomsik68/PermissionsGuru)
+ Very simple API for parameters - standard types are converted automatically, converters for other types can be supplied
+ No workarounds around Bukkit API - uses standard CommandExecutor model without CommandMap hooking
+ Full support for sub-commands

## TODO:
 
+ Improve error messages(make them more specific)
+ Improve sub-commands API

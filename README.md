# ChestPreview
This plugin allow server admin to add preview chest.<br>It can be used to preview kits.

####Depedency:
None.

####Commands:
* /ChestPreview &lt;help&gt; : Display ChestPreview help
* /ChestPreview create : The next placed chest will be a ChestPreview
* /ChestPreview rename : Rename your chest preview
* /ChestPreview list : List all the ChestPreview
* /AddLight [reload] : reload the plugin

####Permissions:
* ChestPreview.admin : Allows creation of ChestPreview to admin users

* ChestPreview.* : given to op
  * ChestPreview.admin

####Config:
The plugin use metrics and an integrated updater.<br>Both can be disable in config.yml

####How to use ChestPreview:
Connect as OP or get the ChestPreview.admin permission.
To create a chest perview, use /chestpreview create, then place your chest or right click an existing chest with a chest in your hand.
You can also define that all the chest in world will be ChestPreview by modifying config.yml
As long as you have the ChestPreview.admin permission you will be able to add, remove or move any item in the chest. Otherwise, you will be able to open the chest but you won't be able to take anything from it.

####Ressources:
Releases : https://github.com/hexosse/ChestPreview/releases

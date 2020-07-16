 cp -r ~/work/adventure-panel/workingdir/* ~/adventure-panel-old
 rm -r ~/adventure-panel-1.0.0-SNAPSHOT
 tar -xvf ~/adventure-panel-1.0.0-SNAPSHOT.tar
 sudo cp -r ~/adventure-panel-1.0.0-SNAPSHOT/* ~/work/adventure-panel/workingdir/
 docker restart adventure-panel; docker logs --tail 2 -f adventure-panel
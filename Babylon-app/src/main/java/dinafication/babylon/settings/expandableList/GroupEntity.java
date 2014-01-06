package dinafication.babylon.settings.expandableList;

import java.util.ArrayList;
import java.util.List;

public class GroupEntity {
    public String Name;
    public String id;
    public List<GroupItemEntity> GroupItemCollection;
   

    public GroupEntity()
    {
        GroupItemCollection = new ArrayList<GroupItemEntity>();
       
       
    }

    public class GroupItemEntity
    {
        public String Name;

        public String note;
        
        public String id;
    }
}
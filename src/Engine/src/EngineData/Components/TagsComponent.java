package Engine.src.EngineData.Components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Deprecated
public class TagsComponent extends Component {
    private List<String> myTags;

    public TagsComponent(String ... tags) {
        myTags = new ArrayList<>();
        for (String tag: tags) {
            myTags.add(tag);
        }
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(myTags);
    }

    public boolean contains(String tag){
        return myTags.contains(tag);
    }

    @Override
    public Component copy() {
        return null;
    }
}

package ECS.Components;

import java.util.List;

public class TagsComponent extends Component {
    private List<String> myTags;

    public TagsComponent(List<String> tags) {
        myTags = tags;
    }
}

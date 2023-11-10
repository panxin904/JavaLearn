package community.community.dto;

import lombok.Data;

/**
 * Created by Coder on 2023/11/9 & 19:37.
 **/
@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}

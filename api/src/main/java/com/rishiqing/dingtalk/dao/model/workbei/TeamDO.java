package com.rishiqing.dingtalk.dao.model.workbei;

/**
 * @author: Yin Jian
 * @create: 2019-03-04 01:19
 */
public class TeamDO {
    private Long teamId;
    private Long activeUserPercent;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getActiveUserPercent() {
        return activeUserPercent;
    }

    public void setActiveUserPercent(Long activeUserPercent) {
        this.activeUserPercent = activeUserPercent;
    }

    @Override
    public String toString() {
        return "TeamDO{" +
                "teamId=" + teamId +
                ", activeUserPercent=" + activeUserPercent +
                '}';
    }
}

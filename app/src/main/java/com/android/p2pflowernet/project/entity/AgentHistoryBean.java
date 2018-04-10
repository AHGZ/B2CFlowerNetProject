package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/12/25 上午10:15
 * description: 代理任务历史
 */
public class AgentHistoryBean implements Serializable {

    /**
     * usercount : 0
     * partnercount : 0
     * agent_reward : 3500.00
     * agent_prize : 没了
     */

    private String usercount;
    private String partnercount;
    private String agent_reward;
    private String agent_prize;

    public String getUsercount() {
        return usercount;
    }

    public void setUsercount(String usercount) {
        this.usercount = usercount;
    }

    public String getPartnercount() {
        return partnercount;
    }

    public void setPartnercount(String partnercount) {
        this.partnercount = partnercount;
    }

    public String getAgent_reward() {
        return agent_reward;
    }

    public void setAgent_reward(String agent_reward) {
        this.agent_reward = agent_reward;
    }

    public String getAgent_prize() {
        return agent_prize;
    }

    public void setAgent_prize(String agent_prize) {
        this.agent_prize = agent_prize;
    }

    @Override
    public String toString() {
        return "AgentHistoryBean{" +
                "usercount='" + usercount + '\'' +
                ", partnercount='" + partnercount + '\'' +
                ", agent_reward='" + agent_reward + '\'' +
                ", agent_prize='" + agent_prize + '\'' +
                '}';
    }
}

package com.example.admin.center.manual.JSON;

import com.example.admin.center.model.GameFile;
import com.example.admin.center.model.GameForm;

import java.util.List;

/**
 * @author shihao
 * @Title: SelectForm
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class SelectForm {
    /**
     * 总数
     */
    private long nums;
    /**
     *
     *
     */
    private List<GameForm> gameForms;

    public long getNums() {
        return nums;
    }

    public void setNums(long nums) {
        this.nums = nums;
    }

    public List<GameForm> getGameForms() {
        return gameForms;
    }

    public void setGameForms(List<GameForm> gameForms) {
        this.gameForms = gameForms;
    }
}

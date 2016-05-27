package org.example.tictactoe;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by kaneshige.koichi on 2016/05/27.
 */
public class Tile {

    public enum Owner {
        X, O, NEITHER, BOTH
    }

    // これらのレベルは、Drawableの定義で指定されている
    private static final int LEVEL_X = 0;
    private static final int LEVEL_O = 1; // letter O
    private static final int LEVEL_BLANK = 2;
    private static final int LEVEL_AVAILABLE = 3;
    private static final int LEVEL_TIE = 3;
    private final GameFragment mGame;
    private Owner mOwner = Owner.NEITHER;
    private View mView;
    private Tile mSubTiles[];

    public Tile(GameFragment game) {
        this.mGame = game;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        this.mView = view;
    }

    public Owner getOwner() {
        return mOwner;
    }

    public void setOwner(Owner owner) {
        this.mOwner = owner;
    }

    public Tile[] getSubTiles() {
        return mSubTiles;
    }

    public void setSubTiles(Tile[] subTiles) {
        this.mSubTiles = subTiles;
    }

    public void updateDrawableState() {
        if (mView == null) {
            return;
        }

        int level = getLevel();
        if (mView.getBackground() != null) {
            mView.getBackground().setLevel(level);
        }
        if (mView instanceof ImageButton) {
            Drawable drawable = ((ImageButton) mView).getDrawable();
            drawable.setLevel(level);
        }
    }

    public Owner findWinner() {
        // オーナーがすでにわかっている場合はそれを返す
        if (getOwner() != Owner.NEITHER) {
            return getOwner();
        }

        int totalX[] = new int[4];
        int totalO[] = new int[4];
        countCaptures(totalX, totalO);

        if (totalX[3] > 0) {
            return Owner.X;
        }
        if (totalO[3] > 0) {
            return Owner.O;
        }

        // 描画の状態をチェックする
        int total = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Owner owner = mSubTiles[3 * row + col].getOwner();
                if (owner != Owner.NEITHER) {
                    total++;
                }
            }
            if (total == 9) {
                return Owner.BOTH;
            }
        }

        // まだどちらもこのマス目のオーナーになっていない
        return Owner.NEITHER;
    }

    private int getLevel() {
        int level = LEVEL_BLANK;
        switch (mOwner) {
            case X:
                level = LEVEL_X;
                break;
            case O:
                level = LEVEL_O;
                break;
            case BOTH:
                level = LEVEL_TIE;
                break;
            case NEITHER:
                level = mGame.isAvailable(this) ? LEVEL_AVAILABLE : LEVEL_BLANK;
                break;
        }
        return level;
    }

    private void countCaptures(int totalX[], int totalO[]) {
        int capturedX, capturedO;

        // 横方向をチェックする
        for (int row = 0; row < 3; row++) {
            capturedX = capturedO = 0;
            for (int col = 0; col < 3; col++) {
                Owner owner = mSubTiles[3 * row + col].getOwner();
                if (owner == Owner.X || owner == Owner.BOTH) {
                    capturedX++;
                }
                if (owner == Owner.O || owner == Owner.BOTH) {
                    capturedO++;
                }
            }
            totalX[capturedX]++;
            totalO[capturedO]++;
        }

        // 縦方向をチェックする
        for (int col = 0; col < 3; col++) {
            capturedX = capturedO = 0;
            for (int row = 0; row < 3; row++) {
                Owner owner = mSubTiles[3 * row + col].getOwner();
                if (owner == Owner.X || owner == Owner.BOTH) {
                    capturedX++;
                }
                if (owner == Owner.O || owner == Owner.BOTH) {
                    capturedO++;
                }
            }
            totalX[capturedX]++;
            totalO[capturedO]++;
        }

        // 対角線をチェックする
        capturedX = capturedO = 0;
        for (int diag = 0; diag < 3; diag++) {
            Owner owner = mSubTiles[3 * diag + diag].getOwner();
            if (owner == Owner.X || owner == Owner.BOTH) {
                capturedX++;
            }
            if (owner == Owner.O || owner == Owner.BOTH) {
                capturedO++;
            }
        }

        totalX[capturedX]++;
        totalO[capturedO]++;
        capturedX = capturedO = 0;
        for (int diag = 0; diag < 3; diag++) {
            Owner owner = mSubTiles[3 * diag + (2 - diag)].getOwner();
            if (owner == Owner.X || owner == Owner.BOTH) {
                capturedX++;
            }
            if (owner == Owner.O || owner == Owner.BOTH) {
                capturedO++;
            }
        }
        totalX[capturedX]++;
        totalO[capturedO]++;
    }

}

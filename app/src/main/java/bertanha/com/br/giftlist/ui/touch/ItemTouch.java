package bertanha.com.br.giftlist.ui.touch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import bertanha.com.br.giftlist.R;
import bertanha.com.br.giftlist.ui.holder.ItemViewHolder;

/**
 * Created by berta on 2/11/2018.
 */

public class ItemTouch extends ItemTouchHelper.SimpleCallback {

    private String TAG = getClass().getName();
    private final FirebaseRecyclerAdapter adapter;
    private Context context;

    private Drawable deleteIcon;
    private int intrinsicWidth;
    private int intrinsicHeight;
    private ColorDrawable deleteBackground;
    private int deleteBackgroundColor;
    private Drawable doneIcon;
    private ColorDrawable doneBackground;
    private int doneBackgroundColor;

    /**
     * Creates a Callback for the given drag and swipe allowance. These values serve as
     * defaults
     * and if you want to customize behavior per ViewHolder, you can override
     * {@link #getSwipeDirs(RecyclerView, RecyclerView.ViewHolder)}
     * and / or {@link #getDragDirs(RecyclerView, RecyclerView.ViewHolder)}.
     *
     * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     */
    public ItemTouch(int dragDirs, int swipeDirs, FirebaseRecyclerAdapter adapter, Context context) {
        super(dragDirs, swipeDirs);
        this.adapter = adapter;
        this.context = context;
        setup();
    }

    private void setup() {
        deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_black_24dp);
        intrinsicWidth = deleteIcon.getIntrinsicWidth();
        intrinsicHeight = deleteIcon.getIntrinsicHeight();
        deleteBackground = new ColorDrawable();
        deleteBackgroundColor = ContextCompat.getColor(context, R.color.colorDanger);

        doneIcon = ContextCompat.getDrawable(context, R.drawable.ic_check_black_24dp);
        doneBackground = new ColorDrawable();
        doneBackgroundColor = ContextCompat.getColor(context, R.color.colorSuccess);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        final DatabaseReference itemKey =  adapter.getRef(position);

        if (direction == ItemTouchHelper.LEFT) {
            try {
                itemKey.removeValue();
                Log.i(TAG, "onSwiped: " + itemKey);
            }catch (Exception e) {
                Log.e(TAG, "onSwiped: " + e.getMessage());
            }
        }else if (direction == ItemTouchHelper.RIGHT) {
            try {
                itemKey.child("ativo").setValue(true);
                Log.i(TAG, "onSwiped: " + itemKey);
            }catch (Exception e) {
                Log.e(TAG, "onSwiped: " + e.getMessage());
            }
        }
    }

    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX <= 0) {

            //Draw the red delete deleteBackground
            deleteBackground.setColor(deleteBackgroundColor);
            deleteBackground.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            deleteBackground.draw(c);

            // Calculate position of delete icon
            int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
            int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
            int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
            int deleteIconRight = itemView.getRight() - deleteIconMargin;
            int deleteIconBottom = deleteIconTop + intrinsicHeight;


            // Draw the delete icon
            deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
            deleteIcon.draw(c);
        } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX > 0) {
            //Draw the red delete deleteBackground
            doneBackground.setColor(doneBackgroundColor);
            doneBackground.setBounds(itemView.getLeft() + (int) dX, itemView.getTop(), itemView.getLeft(), itemView.getBottom());
            doneBackground.draw(c);

            // Calculate position of delete icon
            int doneIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
            int doneIconMargin = (itemHeight + intrinsicHeight) / 2;
            int doneIconLeft = itemView.getLeft() + doneIconMargin - intrinsicWidth;
            int doneIconRight = itemView.getLeft() + doneIconMargin;
            int doneIconBottom = doneIconTop + intrinsicHeight;


            // Draw the delete icon
            doneIcon.setBounds(doneIconLeft, doneIconTop, doneIconRight, doneIconBottom);
            doneIcon.draw(c);
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }
}

package com.marshaldevs.marshalmaterialsmaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SearchView mUrlSearchView;
    Button mPostBtn, mCleanBtn;
    ImageView mAddHashTagBtn;
    ProgressBar mProgressBar;
    EditText mHashTagsEditText;

    FrameLayout mCardMainLayout;
    ImageView mCardImageView;
    TextView mCardTitle, mCardDescription, mCardMainUrl, mCardHashTags, mHashTagWarning;

    TextCrawler mTextCrawler;

    MaterialItem mMaterialItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUrlSearchView = (SearchView) findViewById(R.id.searchview_url);
        mPostBtn = (Button) findViewById(R.id.btnPost);
        mCleanBtn = (Button) findViewById(R.id.btnClean);
        mProgressBar = (ProgressBar) findViewById(R.id.link_preview_progressBar);
        mHashTagsEditText = (EditText) findViewById(R.id.editText_hashTags);
        mAddHashTagBtn = (ImageView) findViewById(R.id.imageview_add_hashtag);

        mCardMainLayout = (FrameLayout) findViewById(R.id.link_preview_frame);
        mCardImageView = (ImageView) findViewById(R.id.thumb);
        mCardTitle = (TextView) findViewById(R.id.title);
        mCardMainUrl = (TextView) findViewById(R.id.url);
        mCardDescription = (TextView) findViewById(R.id.description);
        mCardHashTags = (TextView) findViewById(R.id.tags);
        mHashTagWarning = (TextView) findViewById(R.id.textView_hashTagsWarning);

        mTextCrawler = new TextCrawler();
        mMaterialItem = new MaterialItem();

        mUrlSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mTextCrawler.makePreview(new LinkPreviewCallback() {

                    @Override
                    public void onPre() {
                        switchCardAndProgressVisibility(View.VISIBLE, View.VISIBLE);
                    }

                    @Override
                    public void onPos(SourceContent sourceContent, boolean b) {
                        switchCardAndProgressVisibility(View.VISIBLE, View.GONE);

                        if(sourceContent != null) {

                            mMaterialItem.setUrl(sourceContent.getUrl());

                            if(sourceContent.getTitle() != null) {
                                mCardTitle.setText(sourceContent.getTitle());
                                mMaterialItem.setTitle(sourceContent.getTitle());
                            }
                            if(sourceContent.getDescription() != null) {
                                mCardDescription.setText(sourceContent.getDescription());
                                mMaterialItem.setDescription(sourceContent.getDescription());
                            }
                            if(sourceContent.getCannonicalUrl() != null) {
                                mCardMainUrl.setText(sourceContent.getCannonicalUrl());
                                mMaterialItem.setCannonicalUrl(sourceContent.getCannonicalUrl());
                            }
                            if(sourceContent.getImages() != null && sourceContent.getImages().size() > 0) {
                                mMaterialItem.setImageUrl(sourceContent.getImages().get(0));
                                Picasso.with(MainActivity.this).load(sourceContent.getImages().get(0)).into(mCardImageView);
                            }
                        }
                    }

                }, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mAddHashTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hashTag = mHashTagsEditText.getText().toString();

                if(hashTag.equals("") || hashTag.contains(" ")) {
                    mHashTagWarning.setVisibility(View.VISIBLE);
                } else {
                    mHashTagWarning.setVisibility(View.GONE);
                    hashTag = mCardHashTags.getText().toString() + "#" + hashTag + " ";
                    mCardHashTags.setText(hashTag);
                    mHashTagsEditText.setText("");
                }
            }
        });

        mPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMaterialItem.setTags(mCardHashTags.getText().toString());
                MarshalServiceProvider.getInstance().postRating(mMaterialItem).enqueue(new Callback<MaterialItem>() {
                    @Override
                    public void onResponse(Call<MaterialItem> call, Response<MaterialItem> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                            cleanScreen();
                        } else {
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MaterialItem> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
            }
        });

        mCleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanScreen();
            }
        });
    }

    private void cleanScreen() {
        mCardTitle.setText("");
        mCardDescription.setText("");
        mCardMainUrl.setText("");
        mCardHashTags.setText("");
        mCardImageView.setImageResource(R.drawable.link_image_placeholder);

        mHashTagWarning.setVisibility(View.GONE);
        mHashTagsEditText.setText("");
    }

    public void switchCardAndProgressVisibility(int cardVisibility, int progressBarVisibility) {
        mProgressBar.setVisibility(progressBarVisibility);
        mCardMainLayout.setVisibility(cardVisibility);
    }
}

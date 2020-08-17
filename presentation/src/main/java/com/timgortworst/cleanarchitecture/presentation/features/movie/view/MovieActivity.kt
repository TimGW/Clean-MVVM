package com.timgortworst.cleanarchitecture.presentation.features.movie.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R

class MovieActivity : AppCompatActivity(), MovieListFragment.MovieDetailsClickListener {

    companion object {
        fun intentBuilder(context: Context): Intent {
            return Intent(context, MovieActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_placeholder, MovieListFragment.newInstance())
            transaction.commit()
        }
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onMovieClicked(
        movie: Movie,
        moviePoster: ImageView,
        transitionName: String
    ) {
        replaceFragment(MovieDetailsFragment.newInstance(movie.id, movie.posterPath))
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data) // delegate to fragment
    }
}

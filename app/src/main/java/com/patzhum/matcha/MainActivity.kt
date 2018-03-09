package com.patzhum.matcha

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.MalformedJsonException
import android.view.View
import android.view.ViewGroup

import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.JsonSyntaxException
import com.patzhum.matcha.render.MatchaView
import com.patzhum.matcha.render.core.RenderUtil
import com.patzhum.matcha.render.layouts.MatchaLinearLayout
import com.patzhum.matcha.render.text.MatchaEditText
import com.patzhum.matcha.render.text.MatchaTextView


class MainActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private lateinit var rootLayout : LinearLayout
    private lateinit var dbRootRef : DatabaseReference
    private var rootViewId : Int? = null

    val LOG_TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        checkAuth()
        super.onCreate(savedInstanceState)

        dbRootRef = FirebaseDatabase.getInstance().getReference()

        addActiveEditorContentHook()

        render(null)
    }

    fun addActiveEditorContentHook() {
        dbRootRef.child("users/" + auth.currentUser?.uid + "/activeEditorContent").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                Log.e(LOG_TAG, error.toString());
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                render(snapshot?.getValue(String::class.java))
            }

        })
    }
    fun renderJsonErrorMessage() {
        setContentView(R.layout.render_error)
        val errorView = findViewById<TextView>(R.id.error_message)
        errorView.text = getString(R.string.json_render_error_message)
    }
    fun render(nullableJson : String?) {
        val json = nullableJson ?: "{}"

        var viewRoot: ViewGroup? = null
        rootViewId?.let {
            val contentView = findViewById<ViewGroup>(it)
            viewRoot = contentView?.parent?.let {
                it as ViewGroup
            }
            viewRoot?.removeView(contentView)
        }
        rootLayout = LinearLayout(this)
        rootLayout.id = View.generateViewId()
        rootViewId = rootLayout.id

        val view : View?
        try {
             view = RenderUtil.renderView(json, this)
        } catch (e : MalformedJsonException) {
            renderJsonErrorMessage()
            return
        } catch (e : JsonSyntaxException) {
            renderJsonErrorMessage()
            return
        }

        if (view != null) {
            rootLayout.addView(view)
        }

        viewRoot?.addView(rootLayout) ?: setContentView(rootLayout)

    }

    fun checkAuth() {
        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

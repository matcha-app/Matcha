package com.patzhum.matcha

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import com.patzhum.matcha.render.MatchaTextView
import com.patzhum.matcha.render.RenderUtil

class MainActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private lateinit var rootLayout : LinearLayout
    private lateinit var dbRootRef : DatabaseReference
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

    fun render(nullableJson : String?) {
        val json = nullableJson ?: "{}"
        rootLayout = LinearLayout(this)

        val type = when (RenderUtil.getType(json)) {
            "TextView" -> MatchaTextView::class.java
            else -> null
        }

        if (type != null) {
            val view = RenderUtil.renderView(this, type, json)
            rootLayout.addView(view)
        }

        setContentView(rootLayout)
    }

    fun checkAuth() {
        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

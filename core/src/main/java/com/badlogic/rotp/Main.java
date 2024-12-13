package com.badlogic.rotp;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main implements ApplicationListener {
    Texture backgroundTexture;
    Texture playerTexture;
    Texture playerLTexture;
    Texture playerRTexture;
    Texture playerPTexture;
    Texture sky;
    Sound walkSound;
    Music music;

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Sprite playerSprite;

    @Override
    public void create() {
        backgroundTexture = new Texture("background.png");
        sky = new Texture("sky.png");
        playerTexture = new Texture("walk.png");
        playerLTexture = new Texture("walkL.png");
        playerRTexture = new Texture("walkR.png");
        playerPTexture = new Texture("walkp.png");
        walkSound = Gdx.audio.newSound(Gdx.files.internal("sound.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("musics.mp3"));

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(1, 1);

        music.setLooping(true);
        music.setVolume(.5f);
        music.play();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();


        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerSprite.setTexture(playerRTexture);
            playerSprite.translateX(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerSprite.setTexture(playerLTexture);
            playerSprite.translateX(-speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
           playerSprite.setTexture(playerPTexture);
        } else{
            playerSprite.setTexture(playerTexture);
            walkSound.stop();
        }
    }


    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float playerWidth = playerSprite.getWidth();
        float playerHeight = playerSprite.getHeight();

        playerSprite.setX(MathUtils.clamp(playerSprite.getX(), 0, worldWidth - playerWidth));
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(sky, 0, 0, worldWidth, worldHeight);
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        playerSprite.draw(spriteBatch);

        spriteBatch.end();
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

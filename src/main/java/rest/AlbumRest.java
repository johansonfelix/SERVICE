package rest;

import impl.Albums;
import pojo.Album;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("album")
public class AlbumRest {
    private static Albums albums;


    //change return type
    @POST
    @Consumes("application/xml")
    public void createAlbum(Album album) {

        //check if album ISRC already exists
        //if ISRC exists then return error - album ISRC already exists

        //else add album and return success message

      albums.addAlbum(album);


    }




}

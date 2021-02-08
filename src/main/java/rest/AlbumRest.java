package rest;

import impl.Albums;
import pojo.Album;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.print.Book;


@Path("album")
public class AlbumRest {

    private static Albums albums = new Albums();


    //change return type
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public String addAlbum(Album album) {

        //check if album ISRC already exists
        //if ISRC exists then return error - album ISRC already exists

        if(albums.getAlbum(album.getISRC()) != null){
            return "An album with that ISRC already exists. Please try entering a different one.";
        }
//        //else add album and return success message
        albums.addAlbum(album);
        return "Successfully added album: [" + album.toString()+"]";

    }

    @PUT
    @Path("update/{attribute}/{ISRC}/{newValue}")
    @Consumes("text/plain")
    public String updateTitle(@PathParam("attribute") String attribute, @PathParam("ISRC") String ISRC, @PathParam("newValue") String newValue) {

        if (!albums.albumExists(ISRC))
            return "Album "+attribute+" couldn't be updated. Album does not exist.";


        switch (attribute){
            case "title":{
                albums.getAlbum(ISRC).setTitle(newValue);
                break;
            }

            case "description":{
                albums.getAlbum(ISRC).setDescription(newValue);
                break;
            }

            case "releaseyear":{
                albums.getAlbum(ISRC).setReleaseYear(newValue);
                break;
            }

            case "artist":{
                albums.getAlbum(ISRC).setArtist(newValue);
            }
        }
        return "Album "+attribute+" updated.";
    }


    @PUT
    @Path("update/all")
    @Consumes("application/xml")
    public String updateAlbum(Album album) {
        if (!albums.albumExists(album.getISRC()))
            return "Album couldn't be updated. Album does not exist.";

        else{
            Album oldAlbum = albums.getAlbum(album.getISRC());
            oldAlbum = album;
        }

        return "Album updated";

    }




    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{isrc}")
    public String getAlbum(@PathParam("isrc") String ISRC){
        if(albums.getAlbum(ISRC) == null){
            return "Album not found";
        } else return albums.getAlbum(ISRC).toString();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("listalbums")
    public String listAlbums(){
        return albums.toString();
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete/{ISRC}")
    public String deleteAlbum(@PathParam("ISRC") String ISRC){
        if(albums.getAlbum(ISRC) == null){
            return "Album doesn't exist";
        } else {
            albums.deleteAlbum(ISRC);
            return "Album deleted";
        }
    }







}

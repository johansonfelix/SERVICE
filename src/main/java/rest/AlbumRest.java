package rest;

import impl.Albums;
import pojo.Album;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("album")
public class AlbumRest {

    private static Albums albums = new Albums();

    //change return type
    @POST
    @Consumes("application/xml")
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
    @Path("update/title/{ISRC}/{newValue}")
    @Consumes("text/plain")
    public String updateTitle(@PathParam("ISRC") String ISRC, @PathParam("newValue") String newTitle) {
        if (!albums.albumExists(ISRC))
            return "Album title couldn't be updated. Album does not exist.";

            return "Successfully updated album title.";

    }

//    @PUT
//    @Consumes("application/xml")
//    public String updateAlbum(String ISRC, String attribute, String... values){
//        if(albums.albumExists(ISRC)){
//            return "Album does not exist.";
//        }
//        Album album = albums.getAlbum(ISRC);
//        if(attribute.equals("all")){
//            album.setISRC(values[0]);
//            album.setTitle(values[1]);
//            album.setDescription(values[2]);
//            album.setArtist(values[3]);
//            album.setReleaseYear(values[4]);
//        }
//        switch(attribute){
//            case "title":
//                album.setTitle(values[0]);
//                break;
//            case "description":
//                album.setDescription(values[0]);
//                break;
//            case ""
//
//        }
//        return "Successfully updated album.";
//    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{isrc}")
    public String getAlbum(@PathParam("isrc") String ISRC){
        if(albums.getAlbum(ISRC) == null){
            return "Album not found";
        } else return albums.getAlbum(ISRC).toString();
    }

    /*@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String listAlbums(){
        return albums.toString();
    }*/







}

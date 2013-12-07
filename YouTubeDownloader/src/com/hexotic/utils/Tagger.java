package com.hexotic.utils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.MediaFile;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.blinkenlights.jid3.v1.ID3V1Tag.Genre;
import org.blinkenlights.jid3.v1.ID3V1_0Tag;

public class Tagger {
	
	private String id3Album = "Unknown";
	private String id3Artist = "Unknown";
	private String id3Comment = "";
	private Genre id3Genre = ID3V1Tag.Genre.Other;
	private String id3Title = "Unknown";
	private String id3Year = Calendar.getInstance().get(Calendar.YEAR)+"";

	public Tagger(){
	}
	
	public Object[] getTags(String title){
		try {
			autoGenerateId3(title);
		} catch (IOException e) { }
		Object[] tags = new Object[6];
		tags[0] = id3Title;
		tags[1] = id3Artist;
		tags[2] = id3Album;
		tags[3] = id3Year;
		tags[4] = id3Genre;
		tags[5] = id3Comment;
		return tags;
	}
	
	public void applyID3(String filename, String title) throws ID3Exception, IOException{
		autoGenerateId3(title); // Function attemps to generate id3 tags *IF* not already specified
		// the file we are going to modify
		
		filename = filename.substring(0, filename.length()-4)+".mp3";
		System.out.println(filename);
        File oSourceFile = new File(filename);
        if(oSourceFile.exists()){
	        System.out.println("ID3 Tagining: "+filename);
	        // create an MP3File object representing our chosen file
	        MediaFile oMediaFile = new MP3File(oSourceFile);
	
	        // create a v1.0 tag object, and set some values
	        ID3V1_0Tag oID3V1_0Tag = new ID3V1_0Tag();
	        oID3V1_0Tag.setAlbum(id3Album);
	        oID3V1_0Tag.setArtist(id3Artist);
	        oID3V1_0Tag.setComment(id3Comment);
	        oID3V1_0Tag.setGenre(id3Genre);
	        oID3V1_0Tag.setTitle(id3Title);
	        oID3V1_0Tag.setYear(id3Year);
	       
	        // set this v1.0 tag in the media file object
	        oMediaFile.setID3Tag(oID3V1_0Tag);
	       
	        // update the actual file to reflect the current state of our object 
	        oMediaFile.sync();
        }
	}
	
	private void autoGenerateId3(String title) throws IOException{
		title = title.replaceAll("\\(.*?\\)",""); // Remove shit like (blah blah blah)
		title = title.replaceAll("\\[.*?\\]",""); // Remove shit like [HQ]
		title = title.replaceAll("(?i)lyrics", "");  // Remove when people add the word "lyrics"
		if(id3Artist.equals("Unknown") && id3Title.equals("Unknown")){
			if(title.contains("-")){
				String[] arr = title.split("-");
				id3Artist = arr[0].trim();
				id3Title = arr[1].trim();
			}else{
				id3Title = title.trim();
			}
		}
	}
	
	public String getId3Album() {
		return id3Album;
	}

	public void setId3Album(String id3Album) {
		this.id3Album = id3Album;
	}

	public String getId3Artist() {
		return id3Artist;
	}

	public void setId3Artist(String id3Artist) {
		this.id3Artist = id3Artist;
	}

	public String getId3Comment() {
		return id3Comment;
	}

	public void setId3Comment(String id3Comment) {
		this.id3Comment = id3Comment;
	}

	public Genre getId3Genre() {
		return id3Genre;
	}

	public void setId3Genre(Genre id3Genre) {
		this.id3Genre = id3Genre;
	}

	public String getId3Title() {
		return id3Title;
	}

	public void setId3Title(String id3Title) {
		this.id3Title = id3Title;
	}

	public String getId3Year() {
		return id3Year;
	}

	public void setId3Year(String id3Year) {
		this.id3Year = id3Year;
	}
	public Genre[] getGenres(){
		Genre[] genres = {ID3V1Tag.Genre.Acid,
						  ID3V1Tag.Genre.AcidJazz,
						  ID3V1Tag.Genre.AcidPunk,
						  ID3V1Tag.Genre.Alternative,
						  ID3V1Tag.Genre.AlternativeRock,
						  ID3V1Tag.Genre.Ambient,
						  ID3V1Tag.Genre.Bass,
						  ID3V1Tag.Genre.Blues,
						  ID3V1Tag.Genre.Cabaret,
						  ID3V1Tag.Genre.ChristianRap,
						  ID3V1Tag.Genre.Classical,
						  ID3V1Tag.Genre.ClassicRock,
						  ID3V1Tag.Genre.Comedy,
						  ID3V1Tag.Genre.Country,
						  ID3V1Tag.Genre.Cult,
						  ID3V1Tag.Genre.Dance,
						  ID3V1Tag.Genre.DarkWave,
						  ID3V1Tag.Genre.DeathMetal,
						  ID3V1Tag.Genre.Disco,
						  ID3V1Tag.Genre.Dream,
						  ID3V1Tag.Genre.Electronic,
						  ID3V1Tag.Genre.Ethnic,
						  ID3V1Tag.Genre.EuroDance,
						  ID3V1Tag.Genre.EuroTechno,
						  ID3V1Tag.Genre.Funk,
						  ID3V1Tag.Genre.Fusion,
						  ID3V1Tag.Genre.Game,
						  ID3V1Tag.Genre.Gangsta,
						  ID3V1Tag.Genre.Gospel,
						  ID3V1Tag.Genre.Gothic,
						  ID3V1Tag.Genre.Grunge,
						  ID3V1Tag.Genre.HardRock,
						  ID3V1Tag.Genre.HipHop,
						  ID3V1Tag.Genre.House,
						  ID3V1Tag.Genre.Industrial,
						  ID3V1Tag.Genre.Instrumental,
						  ID3V1Tag.Genre.InstrumentalPop,
						  ID3V1Tag.Genre.InstrumentalRock,
						  ID3V1Tag.Genre.LowFi,
						  ID3V1Tag.Genre.Meditative,
						  ID3V1Tag.Genre.Metal,
						  ID3V1Tag.Genre.Musical,
						  ID3V1Tag.Genre.NativeAmerican,
						  ID3V1Tag.Genre.NewAge,
						  ID3V1Tag.Genre.NewWave,
						  ID3V1Tag.Genre.Noise,
						  ID3V1Tag.Genre.Oldies,
						  ID3V1Tag.Genre.Other,
						  ID3V1Tag.Genre.Polka,
						  ID3V1Tag.Genre.Pop,
						  ID3V1Tag.Genre.PopFolk,
						  ID3V1Tag.Genre.PopFunk,
						  ID3V1Tag.Genre.Pranks,
						  ID3V1Tag.Genre.Psychedelic,
						  ID3V1Tag.Genre.Punk,
						  ID3V1Tag.Genre.Rap,
						  ID3V1Tag.Genre.Rave,
						  ID3V1Tag.Genre.Reggae,
						  ID3V1Tag.Genre.Retro,
						  ID3V1Tag.Genre.RhythmBlues,
						  ID3V1Tag.Genre.Rock,
						  ID3V1Tag.Genre.RockNRoll,
						  ID3V1Tag.Genre.ShowTunes,
						  ID3V1Tag.Genre.Ska,
						  ID3V1Tag.Genre.Soul,
						  ID3V1Tag.Genre.SoundClip,
						  ID3V1Tag.Genre.Soundtrack,
						  ID3V1Tag.Genre.SouthernRock,
						  ID3V1Tag.Genre.Space,
						  ID3V1Tag.Genre.Techno,
						  ID3V1Tag.Genre.TechnoIndustrial,
						  ID3V1Tag.Genre.Top40,
						  ID3V1Tag.Genre.Trailer,
						  ID3V1Tag.Genre.Trance,
						  ID3V1Tag.Genre.Tribal,
						  ID3V1Tag.Genre.TripHop,
						  ID3V1Tag.Genre.Undefined,
						  ID3V1Tag.Genre.Vocal							
		};
		return genres;
	}
}

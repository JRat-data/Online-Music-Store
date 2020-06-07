package cs636.music.presentation.client;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Set;

import cs636.music.config.MusicSystemConfig;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.service.data.CartItemData;
import cs636.music.service.data.DownloadData;
import cs636.music.service.data.InvoiceData;
import cs636.music.service.data.UserData;

public class PresentationUtils {
	// max no. of frames of MP3 to play (a few seconds)
	public static int MP3_FRAMES_TO_PLAY = 200;

	public static void displayInvoices(Set<InvoiceData> invList, PrintStream out) {
		out.println("---------- Invoices--------------");
		out.println("\nId\tUser\tInvoice Date\tAmount");
		for (InvoiceData i : invList) {
			out.print("\n" + i.getInvoiceId() + "\t"
					+ i.getUserFullName() + "\t" + i.getInvoiceDate()
					+ "\t" + i.getTotalAmount() );
		}
		out.println("\n-------------------------------------");

	}
	
	
	public static void displayInvoice(InvoiceData inv, PrintStream out) {	
		out.println("\nId\tUser\t\tInvoice Date\t\tAmount");
		out.print("\n" + inv.getInvoiceId() + "\t"
				+ inv.getUserFullName() + "\t" + inv.getInvoiceDate()
				+ "\t" + inv.getTotalAmount() );
	}
	
	public static void displayProductInfo(Product p, PrintStream out) {
		out.println("----------PRODUCT INFORMATION--------------\n");
		out.print("\nProductCode : " + p.getCode() + "\n Description : "
				+ p.getDescription() + "\n Price : " + p.getPrice());

		out.println("\n----------PRODUCT INFORMATION--------------\n");
	}

	public static void displayCDCatlog(Set<Product> cds, PrintStream out) {
		out.println("----------CD CATALOG--------------");
		out.println("\n ProdCode \t Product Description");
		for (Product cd : cds) {
			out.print("\n" + cd.getCode() + "\t" + cd.getDescription());
		}
		out.println("\n-------------------------------------");
	}

	public static void downloadReport(Set<DownloadData> download, PrintStream out) {
		out.println("\n-----------------Download Report--------------");
		out.println("\nProd Code\tTrack title\tUser\tDL Date");
		for (DownloadData d : download) {

			out.print("\n\t" 
					+ d.getProductCode() + "\t" + d.getTrackTitle()
					+ "\t " + d.getUserEmail() + "\t"
					+ d.getDownloadDate());
		}
		out.println("\n---------------------------------------------------");
	}

	public static void displayCart(Set<CartItemData> cart, PrintStream out) {
		out.println("\n-----------------C A R T--------------");
		if (cart == null)
			out.println("Null cart!");
		else {
			out.println("\n\tProd Code\tQty");
			for (CartItemData item : cart) {
				out.print("\n\t" + item.getCode() + "\t"
						+ item.getQuantity());
			}
		}
		out.println("\n---------------------------------------------------");
	}

	public static void displayTracks(Product product, PrintStream out) {
		Set<Track> tracks = product.getTracks();
		out.println("\n-----------------TRACKS for " + product.getCode()
				+ "--------------");
		out.println("\n\tTrackNo\tFileName\tTitle\tProd Code");
		for (Track t : tracks) {
			out.print("\n\t" + t.getTrackNumber() + "\t"
					+ t.getSampleFilename() + "\t" + t.getTitle() + "\t "
					+ product.getCode());
		}
		out.println("\n---------------------------------------------------");
	}

	public static void playTrack(Track track, PrintStream out) {
		out.println("\n---------------------------------------------------");
		String trackUrl = MusicSystemConfig.SOUND_BASE_URL +
				track.getProduct().getCode() + "/" + track.getSampleFilename();
		out.println("\n Track URL: " + trackUrl);
		out.println("\n ...PLAYING...TRACK..." + track.getTitle() + "...");
		out.println("\n---------------------------------------------------");
		
		// We can use jl1.0.1.jar, library to play MP3s from an input stream (file or URL), 
		// see http://www.javazoom.net/javalayer/javalayer.html
		// Later, with web deployment, we can use the browser.
		// InputStream is = null;
//		try {
//			is = new URL(trackUrl).openStream();
//		} catch (Exception e) {
//			throw new RuntimeException("bad url for mp3 file"+ track.getSampleFilename(), e);
//		}
		try {
			// AdvancedPlayer player = new AdvancedPlayer(is);
			// player.play(MP3_FRAMES_TO_PLAY);  // enough to check it works	
			out.println("Playing " + track.getTitle());
		} catch (Exception e) {
		throw new RuntimeException("problem playing mp3",e);
	}

	}

	public static void displayUserInfo(UserData u, PrintStream out) {
		out.println("\n---------------------------------------------------");
		out.println("\n USER INFORMATION \n");
		out.println("\n Name:" + u.getFirstname() + " " + u.getLastname());
		out.println("\n---------------------------------------------------");
	}

	public static String readEntry(Scanner in, String prompt)
			throws IOException {
		System.out.print(prompt + ":");
		return in.nextLine().trim();
	}

}

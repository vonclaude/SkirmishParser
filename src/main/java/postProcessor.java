import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by vonclaude on 8/11/2017.
 */
public class postProcessor {

    public static void main(String [ ] args) throws IOException {
        postProcessor postProcessor = new postProcessor();

        //Ask user for thread URL
        Scanner threadURLScanner = new Scanner(System.in);
        System.out.println("Enter the URL of the thread: ");
        String threadURL = threadURLScanner.next();

        //Ask user for post that starts Action Phase
        Scanner phaseStartScanner = new Scanner(System.in);
        System.out.print("Enter the post number of the start of the action phase: ");
        int phaseStart = phaseStartScanner.nextInt();

        Scanner outputScanner = new Scanner(System.in);
        System.out.print("Type 'true' to display one player's action at a time. Type 'false' to display all actions at once. ");
        Boolean outputSingle = outputScanner.nextBoolean();

        //Connect to thread, parse HTML for list of posts
        Document doc = Jsoup.connect(threadURL)
                .maxBodySize(0)
                .timeout(600000)
                .get();
        Elements postList = doc.select(".post");

        //Output
        for (Element post : postList) {
            int postNum = Integer.parseInt(post.attr("id").substring(1));

            //Filters for posts after the beginning of Action Phase
            if (phaseStart < postNum) {
                String name = postProcessor.getName(post);
                String dice = postProcessor.getDice(post);
                ArrayList actions = postProcessor.getActions(post);

                //Filters anonymous posts, posts with no Greentext
                if (!name.equals("Anonymous") && !actions.isEmpty()) {
                    System.out.println("-----");
                    System.out.println(name);
                    if (!dice.equals("")) {
                        System.out.println(dice);
                    }
                    System.out.println("");
                    for (Object action : actions) {
                        System.out.println(action);
                    }
                    if (outputSingle == true) {
                        Scanner scanner = new Scanner(System.in);
                        scanner.nextLine();
                    }
                }
            }

            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
    }

    private String getName(Element post) {

        return post.select(".name").first().text();
    }

    private String getDice(Element post) {

        return post.select(".postMessage > b").text();
    }

    private ArrayList getActions(Element post) {
        ArrayList<String> actionList = new ArrayList<String>();
        Elements actions = post.select(".quote");

        for (Element action : actions) {
            actionList.add(action.text());
        }

        return actionList;
    }
}
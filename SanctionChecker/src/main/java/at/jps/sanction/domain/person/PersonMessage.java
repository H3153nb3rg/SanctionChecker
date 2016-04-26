/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.domain.person;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import at.jps.sanction.model.Message;

@Entity
@DiscriminatorValue("PERS")
public class PersonMessage extends Message {

    public static final String fieldNames[]     = { "firstName", "middleName", "lastName", "wholeName", "street", "zipcode", "city", "country", "birthday", "birthplace", "birthcountry" }; // "title",

    /**
     *
     */
    private static final long  serialVersionUID = -1203049679430095362L;
    //
    // private String firstName;
    // private String middleName;
    // private String lastName;
    // private String wholeName;
    // private String title;
    // private String street;
    // private String city;
    // private String country;
    // private String birthday;
    // private String birthplace;
    // private String birthcountry;

    public PersonMessage(final String text) {
        super();
        setRawContent(text);

        parseMessage(text);
    }

    private void parseMessage(String text) {
        // TODO Auto-generated method stub

    }

}

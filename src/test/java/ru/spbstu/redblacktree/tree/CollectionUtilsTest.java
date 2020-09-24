package ru.spbstu.redblacktree.tree;

import ru.spbstu.redblacktree.tree.utils.Assert;
import ru.spbstu.redblacktree.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;
import static ru.spbstu.redblacktree.tree.Person.createNew;

/**
 * provides tests for {@link CollectionUtils}
 */
public class CollectionUtilsTest {
    private static final String TEST_CLASS_NAME = "CollectionUtilsTest";
    private static final String EXCEPTION_STR = " (exception)";
    private static final String FAILED = "failed";

    private CollectionUtilsTest() {
    }

    public static void main(String[] args) {
        testTransformIntoNew();
    }

    private static void testTransformIntoNew() {
        String testName = TEST_CLASS_NAME + ".testTransformIntoNew()";
        Collection<Person> persons = personInit();
        persons.add(createNew("Null", "Nuller", null, 0));
        Collection<String> surnames = CollectionUtils.transformIntoNew(persons, Person::getSurName);
        Collection<String> expected = asList("Angel", "Wayde", "Connor", "Mychailov", null, null);
        Assert.assertEquals(testName, expected, surnames);
        try {
            CollectionUtils.transformIntoNew(persons, null);
            Assert.fail(FAILED);
        } catch (NullPointerException e) {
            Assert.assertEquals(testName + EXCEPTION_STR, "can't handle null", e.getMessage());
        }
        try {
            CollectionUtils.transformIntoNew(null, Objects::toString);
            Assert.fail(FAILED);
        } catch (NullPointerException e) {
            Assert.assertEquals(testName + EXCEPTION_STR, "can't handle null", e.getMessage());
        }
    }

    /**
     * creates new {@link Collection} with some elements for tests
     *
     * @return {@link Collection} with some elements
     */
    private static Collection<Person> personInit() {
        Collection<Person> persons = new ArrayList<>();
        persons.add(createNew("Chris", "", "Angel", 44));
        persons.add(createNew("Robert", "W.", "Wayde", 35));
        persons.add(createNew("John", "jr.", "Connor", 21));
        persons.add(createNew("Misha", "Mihalych", "Mychailov", 52));
        persons.add(null);
        return persons;
    }

    /**
     * creates new Collection with some elements for tests
     *
     * @return Collection with some elements
     */
    private static Collection<String> stringsInit() {
        List<String> strings = new ArrayList<>();
        strings.add("one");
        strings.add("two");
        strings.add(null);
        strings.add("four");
        strings.add("five");
        return strings;
    }
}

class Person {
    private String firstName;
    private String patronymic;
    private String surName;
    private int age;

    public Person(String firstName, String patronymic, String surName, int age) {
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.surName = surName;
        this.age = age;
    }

    public static Person createNew(String firstName, String patronymic, String surName, int age) {
        return new Person(firstName, patronymic, surName, age);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        Person person = (Person) o;
        return getAge() == person.getAge() && Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(
                getPatronymic(), person.getPatronymic()) && Objects.equals(getSurName(), person.getSurName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getPatronymic(), getSurName(), getAge());
    }

    @Override
    public String toString() {
        return surName;
    }
}

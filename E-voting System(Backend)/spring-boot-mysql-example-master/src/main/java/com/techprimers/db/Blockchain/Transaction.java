package com.techprimers.db.Blockchain;

import com.techprimers.db.model.Voter;

import java.util.Objects;

public class Transaction {

    public int id;
    public String Candiate;
    public String Partyname;
    public String PreviousHash;

    public Transaction(int id, String candiate, String partyname, String previousHash) {
        this.id = id;
        Candiate = candiate;
        Partyname = partyname;
        PreviousHash = previousHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id &&
                Objects.equals(Candiate, that.Candiate) &&
                Objects.equals(Partyname, that.Partyname) &&
                Objects.equals(PreviousHash, that.PreviousHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Candiate, Partyname, PreviousHash);
    }
}
